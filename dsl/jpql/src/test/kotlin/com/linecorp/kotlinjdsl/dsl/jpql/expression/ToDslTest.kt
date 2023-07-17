package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression
import org.junit.jupiter.api.Test

class ToDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val nullableInt1: Int? = null

    private val long1: Long = 1

    @Test
    fun `expression to value`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)) to long1
        }

        val actual: ExpressionAndExpression<Long> = expression

        // then
        assertThat(actual).isEqualTo(
            Expressions.pair(
                Expressions.count(Paths.path(TestTable::int1), distinct = false),
                Expressions.value(long1),
            ),
        )
    }

    @Test
    fun `nullable expression to value`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to int1
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Expressions.pair(
                Expressions.max(Paths.path(TestTable::nullableInt1), distinct = false),
                Expressions.value(int1),
            ),
        )
    }

    @Test
    fun `nullable expression to nullable value`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to nullableInt1
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Expressions.pair(
                Expressions.max(Paths.path(TestTable::nullableInt1), distinct = false),
                Expressions.value(nullableInt1),
            ),
        )
    }

    @Test
    fun `expression to expression`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)) to value(long1)
        }

        val actual: ExpressionAndExpression<Long> = expression

        // then
        assertThat(actual).isEqualTo(
            Expressions.pair(
                Expressions.count(Paths.path(TestTable::int1), distinct = false),
                Expressions.value(long1),
            ),
        )
    }

    @Test
    fun `nullable expression to expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to value(int1)
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Expressions.pair(
                Expressions.max(Paths.path(TestTable::nullableInt1), distinct = false),
                Expressions.value(int1),
            ),
        )
    }

    @Test
    fun `nullable expression to nullable expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to value(nullableInt1)
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Expressions.pair(
                Expressions.max(Paths.path(TestTable::nullableInt1), distinct = false),
                Expressions.value(nullableInt1),
            ),
        )
    }

    private class TestTable {
        val int1: Int = 1
        val nullableInt1: Int? = null
    }
}
