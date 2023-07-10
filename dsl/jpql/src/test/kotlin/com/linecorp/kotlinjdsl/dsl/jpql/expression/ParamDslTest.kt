package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Param
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
        assertThat(actual).isEqualTo(Param(paramName1, null))
    }

    @Test
    fun `nullable param name`() {
        // when
        val expression = testJpql {
            param<Int?>(paramName1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Param(paramName1, null))
    }

    @Test
    fun `param name value`() {
        // when
        val expression = testJpql {
            param(paramName1, int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Param(paramName1, int1))
    }

    @Test
    fun `param name nullable value`() {
        // when
        val expression = testJpql {
            param(paramName1, nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Param(paramName1, nullableInt1))
    }

    @Test
    fun `nullable param name value`() {
        // when
        val expression = testJpql {
            param<Int?>(paramName1, int1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Param(paramName1, int1))
    }

    @Test
    fun `nullable param name nullable value`() {
        // when
        val expression = testJpql {
            @Suppress("RemoveExplicitTypeArguments")
            param<Int?>(paramName1, nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(Param(paramName1, nullableInt1))
    }
}
