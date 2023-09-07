package com.linecorp.kotlinjdsl.example.eclipselink.select

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.Book
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.BookPrice
import com.linecorp.kotlinjdsl.example.eclipselink.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.eclipselink.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.eclipselink.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery
import jakarta.persistence.Persistence
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.Test

class SelectExample : WithAssertions {
    private val entityManagerFactory = Persistence.createEntityManagerFactory("example")

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    @Test
    fun `the most prolific author`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).singleResult

        // then
        assertThat(actual).isEqualTo(1L)
    }

    @Test
    fun `authors who haven't written a book`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(listOf(4L))
    }

    @Test
    fun `the book with the most authors`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).singleResult

        // then
        assertThat(actual).isEqualTo("01")
    }

    @Test
    fun `the most expensive book`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).singleResult

        // then
        assertThat(actual).isEqualTo("10")
    }

    @Test
    fun `the most recently published book`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).singleResult

        // then
        assertThat(actual).isEqualTo("10")
    }

    @Test
    fun `books published between January and June 2023`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(
            listOf(
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
            ),
        )
    }

    @Test
    fun `the book with the biggest discounts`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::price)(BookPrice::value).minus(path(Book::salePrice)(BookPrice::value)).desc(),
            )
        }

        // when
        val actual = entityManger.createQuery(jpqlQuery, context).setMaxResults(1).singleResult

        // then
        assertThat(actual).isEqualTo("12")
    }

    @Test
    fun `employees without a nickname`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

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

        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

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
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val jpqlQuery = jpql {
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
        val actual = entityManger.createQuery(jpqlQuery, context).resultList

        // then
        assertThat(actual).isEqualTo(listOf(7L))
    }
}
