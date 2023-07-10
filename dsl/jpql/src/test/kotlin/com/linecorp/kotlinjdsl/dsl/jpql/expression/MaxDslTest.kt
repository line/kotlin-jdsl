package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Max
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Max(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
