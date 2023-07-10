package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Min
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = Min(
            Field<Int?>(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = Min(
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
