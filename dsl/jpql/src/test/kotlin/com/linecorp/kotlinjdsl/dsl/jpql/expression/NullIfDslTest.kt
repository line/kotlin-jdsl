package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class NullIfDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val nullableInt1: Int? = null

    @Test
    fun `nullIf int expression int value`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable1::int1), int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable1::int1),
            compareValue = Expressions.value(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf int expression nullable int value`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable1::int1), nullableInt1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable1::int1),
            compareValue = Expressions.value(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf number expression int value`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable1::number1), int1)
        }.toExpression()

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable1::number1),
            compareValue = Expressions.value(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf number expression nullable int value`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable1::number1), nullableInt1)
        }.toExpression()

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable1::number1),
            compareValue = Expressions.value(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf int expression int expression`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable1::int1), path(TestTable1::int2))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable1::int1),
            compareValue = Paths.path(TestTable1::int2),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf number expression int expression`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable1::number1), path(TestTable1::int2))
        }.toExpression()

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable1::number1),
            compareValue = Paths.path(TestTable1::int2),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    private class TestTable1 {
        val number1: Number = 1

        val int1: Int = 1
        val int2: Int = 2
    }
}
