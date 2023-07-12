package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

@Experimental
class CoalesceDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    private val nullableInt1: Int? = null
    private val nullableInt2: Int? = null

    @Test
    fun `coalesce expression expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::int1), value(int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression nullable expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::int1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::nullableInt1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression with non-null type`() {
        // when
        val expression = testJpql {
            coalesce<Int>(path(TestTable::nullableInt1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression expression expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::int1), value(int1), value(int2))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(int1),
                Value(int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression expression nullable expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::int1), value(int1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(int1),
                Value(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression nullable expression nullable expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::int1), value(nullableInt1), value(nullableInt2))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(nullableInt1),
                Value(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression expression expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::nullableInt1), value(int1), value(int2))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(int1),
                Value(int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression expression nullable expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::nullableInt1), value(int1), value(nullableInt1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(int1),
                Value(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression nullable expression`() {
        // when
        val expression = testJpql {
            coalesce(path(TestTable::nullableInt1), value(nullableInt1), value(nullableInt2))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(nullableInt1),
                Value(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression nullable expression with non-null type`() {
        // when
        val expression = testJpql {
            coalesce<Int>(path(TestTable::nullableInt1), value(nullableInt1), value(nullableInt2))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Coalesce(
            listOf(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(nullableInt1),
                Value(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
