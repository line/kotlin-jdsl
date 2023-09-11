package com.linecorp.kotlinjdsl.example.eclipselink.javax.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.javax.EntityManagerFactoryTestUtils
import com.linecorp.kotlinjdsl.example.eclipselink.javax.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.javax.entity.book.Book
import com.linecorp.kotlinjdsl.example.eclipselink.javax.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.support.eclipselink.javax.extension.createQuery
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class DeleteExample : WithAssertions {
    private val entityManagerFactory = EntityManagerFactoryTestUtils.getEntityManagerFactory()
    private val entityManager = entityManagerFactory.createEntityManager()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun `delete author with id 2`() {
        // when
        val deleteJpqlQuery = jpql {
            deleteFrom(
                entity(Author::class),
            ).where(
                path(Author::authorId).eq(2L),
            )
        }

        val selectJpqlQuery = jpql {
            select(
                entity(Author::class),
            ).from(
                entity(Author::class),
            )
        }

        val actual: List<Author>

        entityManager.transaction.also { tx ->
            tx.begin()

            entityManager.createQuery(deleteJpqlQuery, context).executeUpdate()
            actual = entityManager.createQuery(selectJpqlQuery, context).resultList

            tx.rollback()
        }

        // then
        assertThat(actual.map { it.authorId }).isEqualTo(
            listOf(
                1L,
                3L,
                4L,
            ),
        )
    }

    @Test
    fun `delete all books published after June 2023`() {
        // when
        val deleteJpqlQuery = jpql {
            deleteFrom(
                entity(Book::class),
            ).where(
                path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
            )
        }

        val selectJpqlQuery = jpql {
            select(
                entity(Book::class),
            ).from(
                entity(Book::class),
            )
        }

        val actual: List<Book>

        entityManager.transaction.also { tx ->
            tx.begin()

            entityManager.createQuery(deleteJpqlQuery, context).executeUpdate()
            actual = entityManager.createQuery(selectJpqlQuery, context).resultList

            tx.rollback()
        }

        // then
        assertThat(actual.map { it.isbn }).isEqualTo(
            listOf(
                "01",
                "02",
                "03",
                "04",
                "05",
            ),
        )
    }
}
