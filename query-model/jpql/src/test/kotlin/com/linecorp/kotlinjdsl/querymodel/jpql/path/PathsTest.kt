package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntityProperty
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathProperty
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathTreat
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PathsTest : WithAssertions {
    private val entity1 = Entities.entity(Employee::class)

    private val path1 = Paths.path(EmployeeDepartment::employee)

    @Test
    fun path() {
        // when
        val actual = Paths.path(
            Book::authors,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = Entities.entity(Book::class),
            property = Book::authors,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with an entity`() {
        // when
        val actual = Paths.path(
            entity1,
            Employee::employeeId,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = entity1,
            property = Employee::employeeId,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with a path`() {
        // when
        val actual = Paths.path(
            path1,
            property = Employee::employeeId,
        )

        // then
        val expected = JpqlPathProperty(
            path = path1,
            property = Employee::employeeId,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun treat() {
        // when
        val actual = Paths.treat(
            path = path1,
            type = FullTimeEmployee::class,
        )

        // then
        val expected = JpqlPathTreat(
            path = path1,
            type = FullTimeEmployee::class,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
