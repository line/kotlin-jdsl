package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class MaxDslTest : AbstractJpqlDslTest() {
    @Test
    fun `max expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max expression distinct true`() {
        // when
        val expression = testJpql {
            max(path(TestTable::int1), distinct = true)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max expression distinct false`() {
        // when
        val expression = testJpql {
            max(path(TestTable::int1), distinct = false)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max nullable expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max nullable expression distinct true`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1), distinct = true)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::nullableInt1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max nullable expression distinct false`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1), distinct = false)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `maxDistinct expression`() {
        // when
        val expression = testJpql {
            maxDistinct(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `maxDistinct nullable expression`() {
        // when
        val expression = testJpql {
            maxDistinct(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.max(
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
