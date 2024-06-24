package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.select

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.BookPrice
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.author.AuthorRepository
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.book.BookRepository
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.employee.EmployeeRepository
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Transactional
@SpringBootTest
class SelectExample : WithAssertions {
    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `the most prolific author`() {
        // when
        val actual = authorRepository.findAll {
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
        }.firstNotNullOf { it }

        // then
        assertThat(actual).isEqualTo(1L)
    }

    @Test
    fun `books reference specific book publisher entity`() {
        // when
        val actual = bookRepository.findAll {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
                join(entity(BookPublisher::class)).on(path(BookPublisher::book).eq(entity(Book::class))),
            ).where(
                path(BookPublisher::publisherId).eq(3),
            )
        }

        // then
        assertThat(actual).isEqualTo(listOf(Isbn("10"), Isbn("11"), Isbn("12")))
    }

    @Test
    fun `authors who haven't written a book`() {
        // when
        val actual = authorRepository.findAll {
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

        // then
        assertThat(actual).isEqualTo(listOf(4L))
    }

    @Test
    fun `the list of books`() {
        // given
        val pageable = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "isbn"))

        // when
        val actual = bookRepository.findAll(pageable) {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }

        // then
        assertThat(actual).isEqualTo(listOf(Isbn("04"), Isbn("05"), Isbn("06")))
    }

    @Test
    fun `the page of books`() {
        // given
        val pageable = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "isbn"))

        // when
        val actual = bookRepository.findPage(pageable) {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }

        // then
        assertThat(actual.content).isEqualTo(listOf(Isbn("04"), Isbn("05"), Isbn("06")))
        assertThat(actual.totalPages).isEqualTo(4L)
        assertThat(actual.totalElements).isEqualTo(12L)
    }

    @Test
    fun `the slice of books`() {
        // given
        val pageable = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "isbn"))

        // when
        val actual = bookRepository.findSlice(pageable) {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }

        // then
        assertThat(actual.content).isEqualTo(listOf(Isbn("04"), Isbn("05"), Isbn("06")))
        assertThat(actual.hasNext()).isTrue
    }

    @Test
    fun `the book with the most authors`() {
        // when
        val actual = bookRepository.findAll {
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
        }.firstNotNullOf { it }

        // then
        assertThat(actual).isEqualTo(Isbn("01"))
    }

    @Test
    fun `the most expensive book`() {
        // when
        val actual = bookRepository.findAll {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::salePrice).desc(),
                path(Book::isbn).asc(),
            )
        }.firstNotNullOf { it }

        // then
        assertThat(actual).isEqualTo(Isbn("10"))
    }

    @Test
    fun `the most recently published book`() {
        // when
        val actual = bookRepository.findAll {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::publishDate).desc(),
                path(Book::isbn).asc(),
            )
        }.firstNotNullOf { it }

        // then
        assertThat(actual).isEqualTo(Isbn("12"))
    }

    @Test
    fun `books published between January and June 2023`() {
        // when
        val actual = bookRepository.findAll {
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

        // then
        assertThat(actual).isEqualTo(
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
        // when
        val actual = bookRepository.findAll {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::price)(BookPrice::value).minus(path(Book::salePrice)(BookPrice::value)).desc(),
            )
        }.firstNotNullOf { it }

        // then
        assertThat(actual).isEqualTo(Isbn("12"))
    }

    @Test
    fun `employees without a nickname`() {
        // when
        val actual = employeeRepository.findAll {
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

        // then
        assertThat(actual).isEqualTo(
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

        // when
        val actual = employeeRepository.findAll {
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

        // then
        assertThat(actual).isEqualTo(
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
        data class DerivedEntity(
            val employeeId: Long,
            val count: Long,
        )

        // when
        val actual = employeeRepository.findAll {
            val subquery = select<DerivedEntity>(
                path(Employee::employeeId).`as`(expression("employeeId")),
                count(Employee::employeeId).`as`(expression("count")),
            ).from(
                entity(Employee::class),
                join(Employee::departments),
            ).groupBy(
                path(Employee::employeeId),
            ).having(
                count(Employee::employeeId).greaterThan(1L),
            )

            select(
                count(DerivedEntity::employeeId),
            ).from(
                subquery.asEntity(),
            )
        }

        // then
        assertThat(actual).isEqualTo(listOf(7L))
    }
}
