package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
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
        val expected = NullIf(
            left = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            right = Param(null, int1),
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
        val expected = NullIf(
            left = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            right = Param(null, nullableInt1),
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
        val expected = NullIf(
            left = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            right = Param(null, int1),
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
        val expected = NullIf(
            left = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            right = Param(null, nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
