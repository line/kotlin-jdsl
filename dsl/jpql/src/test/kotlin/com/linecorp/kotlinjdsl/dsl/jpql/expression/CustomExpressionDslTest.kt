package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class CustomExpressionDslTest : WithAssertions {
    private val template1: String = "template1"

    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")

    private val string1 = "string1"
    private val string2 = "string2"

    @Test
    fun `customExpression() with strings`() {
        // when
        val expression = queryPart {
            customExpression(Int::class, template1, string1, string2)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.customExpression(
            Int::class,
            template1,
            listOf(
                Expressions.value(string1),
                Expressions.value(string2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression() with string expressions`() {
        // when
        val expression = queryPart {
            customExpression(Int::class, template1, stringExpression1, stringExpression2)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.customExpression(
            Int::class,
            template1,
            listOf(
                stringExpression1,
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression() with a string and a string expression`() {
        // when
        val expression = queryPart {
            customExpression(Int::class, template1, string1, stringExpression2)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.customExpression(
            Int::class,
            template1,
            listOf(
                Expressions.value(string1),
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
