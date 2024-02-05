package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SizeDslTest : WithAssertions {
    private val path1 = Paths.path(EmployeeDepartment::employee)

    @Test
    fun `size() with property`() {
        // when
        val expression = queryPart {
            size(Employee::departments)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.size(
            path = Paths.path(Employee::departments),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `size() with path`() {
        // when
        val expression = queryPart {
            size(path1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.size(
            path = path1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
