package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class LengthDslTest : WithAssertions {
    private val string1 = "string1"

    private val stringExpression1 = Expressions.value("string1")

    @Test
    fun `length() with a string`() {
        // when
        val expression = queryPart {
            length(string1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.length(
            Expressions.value(string1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `length() with a string expression`() {
        // when
        val expression = queryPart {
            length(stringExpression1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.length(
            stringExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
