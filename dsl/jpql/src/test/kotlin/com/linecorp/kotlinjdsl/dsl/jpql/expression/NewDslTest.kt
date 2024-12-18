package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class NewDslTest : WithAssertions {
    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")

    private val string1 = "string1"
    private val string2 = "string2"

    private class Row

    @Test
    fun `new() with strings`() {
        // when
        val expression = queryPart {
            new(Row::class, string1, string2)
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            type = Row::class,
            args = listOf(
                Expressions.value(string1),
                Expressions.value(string2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new() with string expressions`() {
        // when
        val expression = queryPart {
            new(Row::class, stringExpression1, stringExpression2)
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            type = Row::class,
            args = listOf(
                stringExpression1,
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new() with a string and a string expression`() {
        // when
        val expression = queryPart {
            new(Row::class, string1, stringExpression2)
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            type = Row::class,
            args = listOf(
                Expressions.value(string1),
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
