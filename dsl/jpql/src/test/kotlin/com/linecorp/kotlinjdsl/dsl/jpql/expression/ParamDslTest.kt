package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.junit.jupiter.api.Test

class ParamDslTest : AbstractJpqlDslTest() {
    private val paramName1: String = "paramName1"

    private val int1: Int = 1
    private val nullableInt1: Int? = null

    @Test
    fun `param name`() {
        // when
        val expression = testJpql {
            param<Int>(paramName1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.param(paramName1, null)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `param name int value`() {
        // when
        val expression = testJpql {
            param(paramName1, int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.param(paramName1, int1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `param name nullable int value`() {
        // when
        val expression = testJpql {
            param(paramName1, nullableInt1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.param(paramName1, nullableInt1)

        assertThat(actual).isEqualTo(expected)
    }
}
