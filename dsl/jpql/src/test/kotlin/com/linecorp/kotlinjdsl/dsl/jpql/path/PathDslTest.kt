package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.EmployeeAddress
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PathDslTest : WithAssertions {
    private val entity1 = Entities.entity(FullTimeEmployee::class)

    private val path1 = Paths.treat(Paths.path(EmployeeDepartment::employee), FullTimeEmployee::class)

    @Test
    fun `path() with a property`() {
        // when
        val path = queryPart {
            path(FullTimeEmployee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            FullTimeEmployee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `path() with a entity and a property`() {
        // when
        val path = queryPart {
            entity1.path(FullTimeEmployee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            entity1,
            FullTimeEmployee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `path() with a path and a property`() {
        // when
        val path = queryPart {
            path1.path(FullTimeEmployee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            path1,
            FullTimeEmployee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `path() with a entity and a property of super class`() {
        // when
        val path = queryPart {
            entity1.path(Employee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            entity1,
            Employee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `path() with a path and a property of super class`() {
        // when
        val path = queryPart {
            path1.path(Employee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            path1,
            Employee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `invoke() with a entity and a property`() {
        // when
        val path = queryPart {
            entity1(FullTimeEmployee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            entity1,
            FullTimeEmployee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `invoke() with a path and a property`() {
        // when
        val path = queryPart {
            path1(FullTimeEmployee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            path1,
            FullTimeEmployee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `invoke() with a entity and a property of super class`() {
        // when
        val path = queryPart {
            entity1(Employee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            entity1,
            Employee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `invoke() with a path and a property of super class`() {
        // when
        val path = queryPart {
            path1(Employee::address)
        }

        val actual: Path<EmployeeAddress> = path // for type check

        // then
        val excepted = Paths.path(
            path1,
            Employee::address,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
