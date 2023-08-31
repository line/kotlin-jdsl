package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class NullIfDslTest : WithAssertions {
    private val expression1 = Paths.path(Employee::nickname)

    private val string1 = "string1"

    private val stringExpression1 = Expressions.value("string1")

    @Test
    fun `nullIf() with a string`() {
        // when
        val expression = queryPart {
            nullIf(expression1, string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = expression1,
            compareValue = Expressions.value(string1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf() with a string expression`() {
        // when
        val expression = queryPart {
            nullIf(expression1, stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = expression1,
            compareValue = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }
}
