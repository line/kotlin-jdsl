package com.linecorp.kotlinjdsl.example.eclipselink.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.Book
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.eclipselink.entity.department.Department
import com.linecorp.kotlinjdsl.example.eclipselink.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.eclipselink.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery
import jakarta.persistence.Persistence
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.Test

class DeleteExample : WithAssertions {
    private val entityManagerFactory = Persistence.createEntityManagerFactory("example")

    private val context = JpqlRenderContext()

    @Test
    fun `delete author with id 2`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
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

        // when
        val transaction = entityManger.transaction
        transaction.begin()
        entityManger.createQuery(deleteJpqlQuery, context).executeUpdate()
        transaction.commit()
        val actual = entityManger.createQuery(selectJpqlQuery, context).resultList

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
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
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

        // when
        val transaction = entityManger.transaction
        transaction.begin()
        entityManger.createQuery(deleteJpqlQuery, context).executeUpdate()
        transaction.commit()
        val actual = entityManger.createQuery(selectJpqlQuery, context).resultList

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
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val deleteJpqlQuery = jpql {
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
        val selectJpqlQuery = jpql {
            select(
                entity(Employee::class),
            ).from(
                entity(Employee::class),
            )
        }

        // when
        val transaction = entityManger.transaction
        transaction.begin()
        entityManger.createQuery(deleteJpqlQuery, context).executeUpdate()
        transaction.commit()
        val actual = entityManger.createQuery(selectJpqlQuery, context).resultList

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
