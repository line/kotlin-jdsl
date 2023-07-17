package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class CountDslTest : AbstractJpqlDslTest() {
    @Test
    fun `count expression`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count expression distinct true`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1), distinct = true)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count expression distinct false`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1), distinct = false)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count nullable expression`() {
        // when
        val expression = testJpql {
            count(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count nullable expression distinct true`() {
        // when
        val expression = testJpql {
            count(path(TestTable::nullableInt1), distinct = true)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::nullableInt1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count nullable expression distinct false`() {
        // when
        val expression = testJpql {
            count(path(TestTable::nullableInt1), distinct = false)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `countDistinct expression`() {
        // when
        val expression = testJpql {
            countDistinct(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `countDistinct nullable expression`() {
        // when
        val expression = testJpql {
            countDistinct(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
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
