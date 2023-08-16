package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.department.Department
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.executor.spring.jpa.createQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import jakarta.persistence.EntityManager
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Transactional
@SpringBootTest
class DeleteExample : WithAssertions {
    @Autowired
    private lateinit var context: JpqlRenderContext

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun `delete all books published after June 2023`() {
        // given
        val query = jpql {
            deleteFrom(
                entity(Book::class),
            ).where(
                path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
            )
        }

        // when
        entityManager.createQuery(query, context).executeUpdate()

        val bookIds = jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            ).orderBy(
                path(Book::isbn).asc(),
            )
        }

        val actual = entityManager.createQuery(bookIds, context)

        // then
        assertThat(actual.resultList).isEqualTo(
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
        // given
        val query = jpql {
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
                Employee::class,
            ).where(
                path(Employee::employeeId).`in`(employeeIds),
            )
        }

        // when
        entityManager.createQuery(query, context).executeUpdate()

        val employeeIds = jpql {
            select(
                path(Employee::employeeId),
            ).from(
                entity(Employee::class),
            ).orderBy(
                path(Employee::employeeId).asc(),
            )
        }

        val actual = entityManager.createQuery(employeeIds, context)

        // then
        assertThat(actual.resultList).isEqualTo(
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
