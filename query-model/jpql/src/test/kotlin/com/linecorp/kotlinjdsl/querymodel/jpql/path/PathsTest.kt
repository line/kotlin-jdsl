package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
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
    private val entity2 = Entities.entity(FullTimeEmployee::class)

    private val path1 = Paths.path(EmployeeDepartment::employee)
    private val path2 = Paths.treat(Paths.path(EmployeeDepartment::employee), FullTimeEmployee::class)

    @Test
    fun path() {
        // when
        val actual = Paths.path(
            FullTimeEmployee::address,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = Entities.entity(FullTimeEmployee::class),
            property = FullTimeEmployee::address,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun pathGetter() {
        // when
        val actual = Paths.path(
            FullTimeEmployee::getUpperName,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = Entities.entity(FullTimeEmployee::class),
            property = FullTimeEmployee::getUpperName,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with an entity`() {
        // when
        val actual = Paths.path(
            entity1,
            Employee::address,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = entity1,
            property = Employee::address,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with an entity and a getter`() {
        // when
        val actual = Paths.path(
            entity1,
            Employee::getUpperName,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = entity1,
            property = Employee::getUpperName,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with a path`() {
        // when
        val actual = Paths.path(
            path1,
            property = Employee::address,
        )

        // then
        val expected = JpqlPathProperty(
            path = path1,
            property = Employee::address,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with a path and a getter`() {
        // when
        val actual = Paths.path(
            path1,
            getter = Employee::getUpperName,
        )

        // then
        val expected = JpqlPathProperty(
            path = path1,
            property = Employee::getUpperName,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with an entity and a property of super class`() {
        // when
        val actual = Paths.path(
            entity2,
            Employee::address,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = entity2,
            property = Employee::address,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with an entity and a getter of super class`() {
        // when
        val actual = Paths.path(
            entity2,
            Employee::getUpperName,
        )

        // then
        val expected = JpqlEntityProperty(
            entity = entity2,
            property = Employee::getUpperName,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with a path and a property of super class`() {
        // when
        val actual = Paths.path(
            path2,
            property = Employee::address,
        )

        // then
        val expected = JpqlPathProperty(
            path = path2,
            property = Employee::address,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path() with a path and a getter of super class`() {
        // when
        val actual = Paths.path(
            path2,
            getter = Employee::getUpperName,
        )

        // then
        val expected = JpqlPathProperty(
            path = path2,
            property = Employee::getUpperName,
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
