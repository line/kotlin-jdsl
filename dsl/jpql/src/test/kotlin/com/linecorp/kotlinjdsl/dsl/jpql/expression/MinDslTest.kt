package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class MinDslTest : AbstractJpqlDslTest() {
    @Test
    fun `min expression`() {
        // when
        val expression = testJpql {
            min(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min expression distinct true`() {
        // when
        val expression = testJpql {
            min(path(TestTable::int1), distinct = true)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min expression distinct false`() {
        // when
        val expression = testJpql {
            min(path(TestTable::int1), distinct = false)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min nullable expression`() {
        // when
        val expression = testJpql {
            min(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min nullable expression distinct true`() {
        // when
        val expression = testJpql {
            min(path(TestTable::nullableInt1), distinct = true)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::nullableInt1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min nullable expression distinct false`() {
        // when
        val expression = testJpql {
            min(path(TestTable::nullableInt1), distinct = false)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `minDistinct expression`() {
        // when
        val expression = testJpql {
            minDistinct(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `minDistinct nullable expression`() {
        // when
        val expression = testJpql {
            minDistinct(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.min(
            Paths.path(TestTable::nullableInt1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
