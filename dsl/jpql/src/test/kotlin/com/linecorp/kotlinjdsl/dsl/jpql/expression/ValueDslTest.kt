package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Value
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
        assertThat(actual).isEqualTo(Value(int1))
    }

    @Test
    fun `nullable value`() {
        // when
        val expression = testJpql {
            value(nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Value(nullableInt1))
    }

    @Test
    fun `null value`() {
        // when
        val expression = testJpql {
            nullValue<Int?>()
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Value(null))
    }
}
