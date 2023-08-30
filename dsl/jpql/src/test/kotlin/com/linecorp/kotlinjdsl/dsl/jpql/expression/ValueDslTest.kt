@file:Suppress("RedundantNullableReturnType")

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.junit.jupiter.api.Test

class ValueDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 100

    @Test
    fun `value int`() {
        // when
        val expression = testJpql {
            value(int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.value(
            int1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun nullValue() {
        // when
        val expression = testJpql {
            nullValue<Int>()
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.nullValue<Int>()

        assertThat(actual).isEqualTo(expected)
    }
}
