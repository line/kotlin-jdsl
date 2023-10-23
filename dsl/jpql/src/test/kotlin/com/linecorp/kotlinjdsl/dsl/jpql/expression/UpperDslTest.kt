package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class UpperDslTest : WithAssertions {
    private val string1 = "string1"

    private val stringExpression1 = Expressions.value("string1")

    @Test
    fun `upper() with a string`() {
        // when
        val expression = queryPart {
            upper(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.upper(
            Expressions.value(string1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `upper() with a string expression`() {
        // when
        val expression = queryPart {
            upper(stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.upper(
            stringExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
