package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class ExpressionDslTest : WithAssertions {
    private val alias1 = "alias1"

    @Test
    fun `expression() with a class and an alias`() {
        // when
        val expression = queryPart {
            expression(String::class, alias1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.expression(
            String::class,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression() with a generic type and an alias`() {
        // when
        val expression = queryPart {
            expression<String>(alias1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.expression(
            String::class,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
