package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class TrimTrailingDslTest : WithAssertions {
    private val char1 = 'c'
    private val string1 = "string1"

    private val charExpression1 = Expressions.value(char1)
    private val stringExpression1 = Expressions.value(string1)

    @Test
    fun `trimTrailing() without a char, from() with a string`() {
        // when
        val expression = queryPart {
            trimTrailing().from(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.trimTrailing(
            value = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `trimTrailing() without a char, from() with a string expression`() {
        // when
        val expression = queryPart {
            trimTrailing().from(stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.trimTrailing(
            value = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `trimTrailing() with a char, from() with a string`() {
        // when
        val expression = queryPart {
            trimTrailing(char1).from(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.trimTrailing(
            character = charExpression1,
            value = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `trimTrailing() with a char, from() with a string expression`() {
        // when
        val expression = queryPart {
            trimTrailing(char1).from(stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.trimTrailing(
            character = charExpression1,
            value = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `trimTrailing() with a char expression, from() with a string`() {
        // when
        val expression = queryPart {
            trimTrailing(charExpression1).from(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.trimTrailing(
            character = charExpression1,
            value = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `trimTrailing() with a char expression, from() with a string expression`() {
        // when
        val expression = queryPart {
            trimTrailing(charExpression1).from(stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.trimTrailing(
            character = charExpression1,
            value = stringExpression1,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }
}
