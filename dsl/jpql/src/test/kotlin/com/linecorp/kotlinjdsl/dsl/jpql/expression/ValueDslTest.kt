package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class ValueDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val nullableInt1: Int? = null

    @Test
    fun value() {
        // when
        val expression = testJpql {
            value(int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        assertThat(actual).isEqualTo(
            Expressions.value(int1),
        )
    }

    @Test
    fun `nullable value`() {
        // when
        val expression = testJpql {
            value(nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(
            Expressions.value(nullableInt1),
        )
    }

    @Test
    fun `null value`() {
        // when
        val expression = testJpql {
            nullValue<Int?>()
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(
            Expressions.value(null),
        )
    }
}
