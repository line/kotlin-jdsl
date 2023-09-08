package com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.hibernate.EntityManagerTestUtils
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.department.Department
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.jpql.hibernate.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.support.hibernate.extension.createQuery
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class DeleteExample : WithAssertions {
    private val entityManager = EntityManagerTestUtils.getEntityManager()

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
            select(path(Book::isbn))
                .from(entity(Book::class))
        }

        val actual: List<Isbn>

        entityManager.transaction.let { tx ->
            tx.begin()

            entityManager.createQuery(deleteQuery, context).executeUpdate()
            actual = entityManager.createQuery(selectQuery, context).resultList

            tx.rollback()
        }

        // then
        assertThat(actual).isEqualTo(
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
    fun `remove the employees from department 03`() {
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
            select(path(Employee::employeeId))
                .from(entity(Employee::class))
        }

        val actual: List<Long>

        entityManager.transaction.let { tx ->
            tx.begin()

            entityManager.createQuery(deleteQuery, context).executeUpdate()
            actual = entityManager.createQuery(selectQuery, context).resultList

            tx.rollback()
        }

        // then
        assertThat(actual).isEqualTo(
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
