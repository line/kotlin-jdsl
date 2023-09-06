package com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.HibernateExample
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.department.Department
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.employee.EmployeeDepartment
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class DeleteExample : WithAssertions, HibernateExample() {
    @Test
    fun `delete all books published after June 2023`() = withTransaction {
        // when
        entityManager.createQuery(
            jpql {
                deleteFrom(
                    entity(Book::class),
                ).where(
                    path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
                )
            },
        ).executeUpdate()

        val actual = entityManager.createQuery(
            jpql {
                select(path(Book::isbn))
                    .from(entity(Book::class))
            },
        ).resultList

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
    fun `remove the employees from department 03`() = withTransaction {
        // when
        entityManager.createQuery(
            jpql {
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
            },
        ).executeUpdate()

        val actual = entityManager.createQuery(
            jpql {
                select(entity(Employee::class))
                    .from(entity(Employee::class))
            },
        ).resultList.map { it.employeeId }

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
