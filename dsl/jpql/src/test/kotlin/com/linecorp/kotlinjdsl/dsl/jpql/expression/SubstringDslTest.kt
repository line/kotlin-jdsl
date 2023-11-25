package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SubstringDslTest : WithAssertions {
    private val string1 = "string1"
    private val int1 = 1
    private val int2 = 2

    private val stringExpression1 = Expressions.value("string1")
    private val intExpression1 = Expressions.value(1)
    private val intExpression2 = Expressions.value(2)

    @Test
    fun `substring() with a string and an int`() {
        // when
        val expression = queryPart {
            substring(string1, int1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.substring(
            value = Expressions.value(string1),
            start = Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `substring() with a string and ints`() {
        // when
        val expression = queryPart {
            substring(string1, int1, int2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.substring(
            value = Expressions.value(string1),
            start = Expressions.value(int1),
            length = Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `substring() with a string expression and an int`() {
        // when
        val expression = queryPart {
            substring(stringExpression1, int1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.substring(
            value = stringExpression1,
            start = Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `substring() with a string expression and ints`() {
        // when
        val expression = queryPart {
            substring(stringExpression1, int1, int2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.substring(
            value = stringExpression1,
            start = Expressions.value(int1),
            length = Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `substring() with a string expression and an int expression`() {
        // when
        val expression = queryPart {
            substring(stringExpression1, intExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.substring(
            value = stringExpression1,
            start = intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `substring() with a string expression and int expressions`() {
        // when
        val expression = queryPart {
            substring(stringExpression1, intExpression1, intExpression2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.substring(
            value = stringExpression1,
            start = intExpression1,
            length = intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
