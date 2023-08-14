package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.select

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.author.Author
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.book.BookPrice
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.createQuery
import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.queryForPage
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import javax.persistence.EntityManager

@Transactional
@SpringBootTest
class SelectExample : WithAssertions {
    @Autowired
    private lateinit var context: JpqlRenderContext

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun `the most prolific author`() {
        // given
        val query = jpql {
            select(
                path(Author::authorId),
            ).from(
                entity(Author::class),
                join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
            ).groupBy(
                path(Author::authorId),
            ).orderBy(
                count(Author::authorId).desc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context).apply {
            maxResults = 1
        }

        // then
        assertThat(actual.resultList).isEqualTo(listOf(1L))
    }

    @Test
    fun `authors who haven't written a book`() {
        // given
        val query = jpql {
            select(
                path(Author::authorId),
            ).from(
                entity(Author::class),
                leftJoin(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
            ).where(
                path(BookAuthor::authorId).isNull(),
            ).orderBy(
                path(Author::authorId).asc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context)

        // then
        assertThat(actual.resultList).isEqualTo(listOf(4L))
    }

    @Test
    fun books() {
        // given
        val query = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }

        val pageable = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "isbn"))

        // when
        val actual = entityManager.queryForPage(query, pageable, context)

        // then
        assertThat(actual.content).isEqualTo(listOf(Isbn("04"), Isbn("05"), Isbn("06")))
        assertThat(actual.totalPages).isEqualTo(4L)
        assertThat(actual.totalElements).isEqualTo(12L)
    }

    @Test
    fun `the book with the most authors`() {
        // given
        val query = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
                join(Book::authors),
            ).groupBy(
                path(Book::isbn),
            ).orderBy(
                count(Book::isbn).desc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context).apply {
            maxResults = 1
        }

        // then
        assertThat(actual.resultList).isEqualTo(listOf(Isbn("01")))
    }

    @Test
    fun `the most expensive book`() {
        // given
        val query = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::salePrice).desc(),
                path(Book::isbn).asc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context).apply {
            maxResults = 1
        }

        // then
        assertThat(actual.resultList).isEqualTo(listOf(Isbn("10")))
    }

    @Test
    fun `the most recently published book`() {
        // given
        val query = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::salePrice).desc(),
                path(Book::isbn).asc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context).apply {
            maxResults = 1
        }

        // then
        assertThat(actual.resultList).isEqualTo(listOf(Isbn("10")))
    }

    @Test
    fun `books published between January and June 2023`() {
        // given
        val query = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).where(
                path(Book::publishDate).between(
                    OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
                    OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
                ),
            ).orderBy(
                path(Book::isbn).asc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context)

        // then
        assertThat(actual.resultList).isEqualTo(
            listOf(
                Isbn("01"),
                Isbn("02"),
                Isbn("03"),
                Isbn("04"),
                Isbn("05"),
                Isbn("06"),
            ),
        )
    }

    @Test
    fun `the book with the biggest discounts`() {
        // given
        val query = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::price)(BookPrice::value).minus(
                    path(
                        Book::salePrice,
                    )(BookPrice::value),
                ).desc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context).apply {
            maxResults = 1
        }

        // then
        assertThat(actual.resultList).isEqualTo(listOf(Isbn("12")))
    }

    @Test
    fun `employees without a nickname`() {
        // given
        val query = jpql {
            select(
                path(Employee::employeeId),
            ).from(
                entity(Employee::class),
            ).where(
                path(Employee::nickname).isNull(),
            ).orderBy(
                path(Employee::employeeId).asc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context)

        // then
        assertThat(actual.resultList).isEqualTo(
            listOf(
                1L,
                2L,
                3L,
                4L,
                5L,
                6L,
                7L,
                16L,
                17L,
                18L,
                19L,
                20L,
                21L,
                22L,
                23L,
            ),
        )
    }

    @Test
    fun the_number_of_employees_per_department() {
        // given
        data class Row(
            val departmentId: Long,
            val count: Long,
        )

        val query = jpql {
            selectNew<Row>(
                path(EmployeeDepartment::departmentId),
                count(Employee::employeeId),
            ).from(
                entity(Employee::class),
                join(Employee::departments),
            ).groupBy(
                path(EmployeeDepartment::departmentId),
            ).orderBy(
                path(EmployeeDepartment::departmentId).asc(),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context)

        // then
        assertThat(actual.resultList).isEqualTo(
            listOf(
                Row(1, 6),
                Row(2, 15),
                Row(3, 18),
            ),
        )
    }

    @Test
    fun `the number of employees who belong to more than one department`() {
        // given
        val query = jpql {
            val subquery = select(
                path(Employee::employeeId),
            ).from(
                entity(Employee::class),
                join(Employee::departments),
            ).groupBy(
                path(Employee::employeeId),
            ).having(
                count(Employee::employeeId).greaterThan(1L),
            ).asSubquery()

            select(
                count(Employee::employeeId),
            ).from(
                entity(Employee::class),
            ).where(
                path(Employee::employeeId).`in`(subquery),
            )
        }

        // when
        val actual = entityManager.createQuery(query, context)

        // then
        assertThat(actual.resultList).isEqualTo(listOf(7L))
    }
}