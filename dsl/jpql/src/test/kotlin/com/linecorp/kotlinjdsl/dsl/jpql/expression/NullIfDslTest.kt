package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class NullIfDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1

    private val nullableInt1: Int? = null

    @Test
    fun `nullIf expression expression`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable::int1), value(int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable::int1),
            compareValue = Expressions.value(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf expression nullable expression`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable::int1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable::int1),
            compareValue = Expressions.value(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf nullable expression expression`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable::nullableInt1), value(int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable::nullableInt1),
            compareValue = Expressions.value(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf nullable expression nullable expression`() {
        // when
        val expression = testJpql {
            nullIf(path(TestTable::nullableInt1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.nullIf(
            value = Paths.path(TestTable::nullableInt1),
            compareValue = Expressions.value(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
