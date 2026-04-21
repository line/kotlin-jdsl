package com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.select

import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.repository.book.BookRepository
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class Issue1074Test : WithAssertions {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    fun `findPage with explicit count query returns distinct totalElements for joined query with groupBy`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - main query joins authors and groups by ISBN (12 groups),
        //        count query uses countDistinct to return the true group count.
        val actual = bookRepository.findPage(
            pageable,
            {
                select(path(Book::isbn))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    ).groupBy(path(Book::isbn))
            },
            {
                select(countDistinct(path(Book::isbn)))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    )
            },
        )

        // then - 12 distinct books (auto count would return 15 joined rows)
        assertThat(actual.totalElements).isEqualTo(12L)
        assertThat(actual.content).hasSize(10)
    }

    @Test
    fun `findPage with explicit count query honors where clause`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - filter to ISBNs 01..03 in both main and count query
        val actual = bookRepository.findPage(
            pageable,
            {
                select(path(Book::isbn))
                    .from(entity(Book::class))
                    .where(path(Book::isbn).`in`(Isbn("01"), Isbn("02"), Isbn("03")))
            },
            {
                select(countDistinct(path(Book::isbn)))
                    .from(entity(Book::class))
                    .where(path(Book::isbn).`in`(Isbn("01"), Isbn("02"), Isbn("03")))
            },
        )

        // then
        assertThat(actual.totalElements).isEqualTo(3L)
        assertThat(actual.content).hasSize(3)
    }

    @Test
    fun `findPage with count query that uses groupBy falls back to row count`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - count query itself has groupBy -> resultList.size > 1,
        //        impl should fall back to counts.count().toLong().
        val actual = bookRepository.findPage(
            pageable,
            {
                select(entity(Book::class))
                    .from(entity(Book::class))
            },
            {
                select(count(Book::isbn))
                    .from(entity(Book::class))
                    .groupBy(path(Book::isbn))
            },
        )

        // then - 12 distinct ISBNs -> groupBy returns 12 rows -> counts.count() = 12
        assertThat(actual.totalElements).isEqualTo(12L)
        assertThat(actual.content).hasSize(10)
    }

    @Test
    fun `findPage skips count query when content fits within page`() {
        // Spring's PageableExecutionUtils skips the count supplier when
        // pageable is on page 0 and content.size < pageSize.
        // Prove the supplier isn't invoked by giving a count query that
        // would return 0 (wrong total). If it ran, totalElements would
        // be 0; instead Spring derives totalElements from content.size.
        val pageable = PageRequest.of(0, 100)

        val actual = bookRepository.findPage(
            pageable,
            {
                select(path(Book::isbn))
                    .from(entity(Book::class))
            },
            {
                select(countDistinct(path(Book::isbn)))
                    .from(entity(Book::class))
                    .where(path(Book::isbn).eq(Isbn("99"))) // matches nothing
            },
        )

        // content.size = 12, pageSize = 100 -> count query is NOT executed.
        // totalElements = pageable.offset (0) + content.size (12) = 12.
        assertThat(actual.totalElements).isEqualTo(12L)
        assertThat(actual.content).hasSize(12)
    }

    @Test
    fun `findPage with explicit count query paginates across pages`() {
        // given
        val firstPageable = PageRequest.of(0, 5)
        val secondPageable = PageRequest.of(1, 5)

        // when
        val firstPage = bookRepository.findPage(
            firstPageable,
            {
                select(path(Book::isbn))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    ).groupBy(path(Book::isbn))
            },
            {
                select(countDistinct(path(Book::isbn)))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    )
            },
        )
        val secondPage = bookRepository.findPage(
            secondPageable,
            {
                select(path(Book::isbn))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    ).groupBy(path(Book::isbn))
            },
            {
                select(countDistinct(path(Book::isbn)))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    )
            },
        )

        // then - 12 total, 5 + 5 split across pages
        assertThat(firstPage.totalElements).isEqualTo(12L)
        assertThat(firstPage.content).hasSize(5)

        assertThat(secondPage.totalElements).isEqualTo(12L)
        assertThat(secondPage.content).hasSize(5)
        assertThat(secondPage.content).isNotEqualTo(firstPage.content)
    }
}
