package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@Experimental
class CoalesceDslTest : WithAssertions {
    private val stringExpression1 = Paths.path(Employee::nickname)
    private val stringExpression2 = Paths.path(Employee::name)
    private val stringExpression3 = Expressions.value("string3")

    private val string1 = "string1"
    private val string2 = "string2"

    @Test
    fun `coalesce() with strings`() {
        // when
        val expression = queryPart {
            coalesce(stringExpression1, string1, string2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.coalesce(
            value = stringExpression1,
            alternate = Expressions.value(string1),
            others = listOf(Expressions.value(string2)),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce() with string expressions`() {
        // when
        val expression = queryPart {
            coalesce(stringExpression1, stringExpression2, stringExpression3)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.coalesce(
            value = stringExpression1,
            alternate = stringExpression2,
            others = listOf(stringExpression3),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
