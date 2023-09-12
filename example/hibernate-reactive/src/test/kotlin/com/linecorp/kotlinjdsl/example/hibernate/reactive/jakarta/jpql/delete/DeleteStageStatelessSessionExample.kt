package com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.hibernate.reactive.SessionFactoryTestUtils
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.department.Department
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class DeleteStageStatelessSessionExample : WithAssertions {
    private val sessionFactory = SessionFactoryTestUtils.getStageSessionFactory()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    @Test
    fun `delete all books published after June 2023`() {
        // when
        val deleteQuery = jpql {
            deleteFrom(
                entity(Book::class),
            ).where(
                path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
            )
        }

        val selectQuery = jpql {
            select(
                entity(Book::class),
            ).from(
                entity(Book::class),
            )
        }

        val actual = sessionFactory.withStatelessTransaction { session, tx ->
            tx.markForRollback()

            session.createQuery(deleteQuery, context).executeUpdate()
                .thenCompose { session.createQuery(selectQuery, context).resultList }
        }.toCompletableFuture().get()

        // then
        assertThat(actual.map { it.isbn }).isEqualTo(
            listOf(
                Isbn("01"),
                Isbn("02"),
                Isbn("03"),
                Isbn("04"),
                Isbn("05"),
            ),
        )
    }

    @Test
    fun `delete the employees from department 03`() {
        // when
        val deleteQuery = jpql {
            val employeeIds = select<Long>(
                path(EmployeeDepartment::employee)(Employee::employeeId),
            ).from(
                entity(Department::class),
                join(EmployeeDepartment::class)
                    .on(path(Department::departmentId).equal(path(EmployeeDepartment::departmentId))),
            ).where(
                path(Department::name).like("%03"),
            ).asSubquery()

            deleteFrom(
                entity(Employee::class),
            ).where(
                path(Employee::employeeId).`in`(employeeIds),
            )
        }

        val selectQuery = jpql {
            select(
                entity(Employee::class),
            ).from(
                entity(Employee::class),
            )
        }

        val actual = sessionFactory.withStatelessTransaction { session, tx ->
            tx.markForRollback()

            session.createQuery(deleteQuery, context).executeUpdate()
                .thenCompose { session.createQuery(selectQuery, context).resultList }
        }.toCompletableFuture().get()

        // then
        assertThat(actual.map { it.employeeId }).isEqualTo(
            listOf(
                2L,
                3L,
                4L,
                5L,
                6L,
                15L,
                16L,
                17L,
                18L,
                19L,
                20L,
                22L,
            ),
        )
    }
}
