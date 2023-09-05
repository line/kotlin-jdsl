package com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.SessionFactoryCreator
import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.department.Department
import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.hibernate.reactive.stage.Stage.SessionFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteStageStatelessSessionExample : WithAssertions {
    private lateinit var sessionFactory: SessionFactory

    private val context = JpqlRenderContext()

    @BeforeEach
    fun before() {
        sessionFactory = SessionFactoryCreator.stageSessionFactory()
    }

    @Test
    fun `delete all books published after June 2023`() {
        val query = jpql {
            deleteFrom(
                entity(Book::class),
            ).where(
                path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
            )
        }
        // when

        sessionFactory.withStatelessSession {
            it.createQuery(query, context).executeUpdate()
        }.toCompletableFuture().get()

        val actual = sessionFactory.withStatelessSession {
            it.createQuery(
                jpql {
                    select(
                        entity(Book::class),
                    ).from(
                        entity(Book::class),
                    )
                },
                context,
            ).resultList
        }.toCompletableFuture().get().map { it.isbn }

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
                entity(Employee::class),
            ).where(
                path(Employee::employeeId).`in`(employeeIds),
            )
        }

        sessionFactory.withStatelessSession {
            it.createQuery(query, context).executeUpdate()
        }.toCompletableFuture().get()

        val actual = sessionFactory.withStatelessSession {
            it.createQuery(
                jpql {
                    select(
                        entity(Employee::class),
                    ).from(
                        entity(Employee::class),
                    )
                },
                context,
            ).resultList
        }.toCompletableFuture().get().map { it.employeeId }

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
