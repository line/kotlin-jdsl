package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.select

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.author.AuthorRepository
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class Issue1242Test : WithAssertions {
    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun `notIn with an empty collection does not filter out any rows`() {
        // when
        val actual =
            authorRepository.findAll {
                select(
                    path(Author::authorId),
                ).from(
                    entity(Author::class),
                ).where(
                    path(Author::authorId).notIn(emptyList<Long>()),
                ).orderBy(
                    path(Author::authorId).asc(),
                )
            }

        // then - 4 authors in data.sql, none of them is filtered out
        assertThat(actual).isEqualTo(listOf(1L, 2L, 3L, 4L))
    }
}
