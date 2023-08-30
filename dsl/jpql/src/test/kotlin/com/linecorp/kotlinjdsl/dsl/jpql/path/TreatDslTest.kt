package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class TreatDslTest : WithAssertions {
    private val path1 = Paths.path(EmployeeDepartment::employee)

    @Test
    fun treat() {
        // when
        val path = queryPart {
            path1.treat(FullTimeEmployee::class)
        }

        val actual: Path<FullTimeEmployee> = path // for type check

        // then
        val expected = Paths.treat(
            path1,
            FullTimeEmployee::class,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
