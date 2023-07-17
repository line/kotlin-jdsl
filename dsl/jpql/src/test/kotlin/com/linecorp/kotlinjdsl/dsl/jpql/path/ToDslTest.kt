package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression
import org.junit.jupiter.api.Test

class ToDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val nullableInt1: Int? = null

    @Test
    fun `path to value`() {
        // when
        val expression = testJpql {
            path(TestTable::int1) to int1
        }

        val actual: PathAndExpression<Int> = expression

        // then
        assertThat(actual).isEqualTo(
            Paths.pair(Paths.path(TestTable::int1), Expressions.value(int1)),
        )
    }

    @Test
    fun `nullable path to value`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to int1
        }

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Paths.pair(Paths.path(TestTable::nullableInt1), Expressions.value(int1)),
        )
    }

    @Test
    fun `nullable path to nullable value`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to nullableInt1
        }

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Paths.pair(Paths.path(TestTable::nullableInt1), Expressions.value(nullableInt1)),
        )
    }

    @Test
    fun `path to expression`() {
        // when
        val expression = testJpql {
            path(TestTable::int1) to value(int1)
        }

        val actual: PathAndExpression<Int> = expression

        // then
        assertThat(actual).isEqualTo(
            Paths.pair(Paths.path(TestTable::int1), Expressions.value(int1)),
        )
    }

    @Test
    fun `nullable path to expression`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to value(int1)
        }

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Paths.pair(Paths.path(TestTable::nullableInt1), Expressions.value(int1)),
        )
    }

    @Test
    fun `nullable path to nullable expression`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to value(nullableInt1)
        }

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            Paths.pair(Paths.path(TestTable::nullableInt1), Expressions.value(nullableInt1)),
        )
    }

    private class TestTable {
        val int1: Int = 1
        val nullableInt1: Int? = null
    }
}
