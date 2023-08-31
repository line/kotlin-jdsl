package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class ParamDslTest : WithAssertions {
    private val name1: String = "name1"

    private val string1 = "string1"

    @Test
    fun `param() with a name`() {
        // when
        val expression = queryPart {
            param<String>(name1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.param(
            name = name1,
            value = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `param() with a name and a value`() {
        // when
        val expression = queryPart {
            param(name1, string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.param(
            name = name1,
            value = string1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
