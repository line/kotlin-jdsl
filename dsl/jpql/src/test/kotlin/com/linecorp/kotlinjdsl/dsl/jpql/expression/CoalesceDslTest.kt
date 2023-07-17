package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::int1),
            listOf(
                Expressions.value(int1),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::int1),
            listOf(
                Expressions.value(nullableInt1),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::nullableInt1),
            listOf(
                Expressions.value(nullableInt1),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::nullableInt1),
            listOf(
                Expressions.value(nullableInt1),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::int1),
            listOf(
                Expressions.value(int1),
                Expressions.value(int2),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::int1),
            listOf(
                Expressions.value(int1),
                Expressions.value(nullableInt1),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::int1),
            listOf(
                Expressions.value(nullableInt1),
                Expressions.value(nullableInt2),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::nullableInt1),
            listOf(
                Expressions.value(int1),
                Expressions.value(int2),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::nullableInt1),
            listOf(
                Expressions.value(int1),
                Expressions.value(nullableInt1),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::nullableInt1),
            listOf(
                Expressions.value(nullableInt1),
                Expressions.value(nullableInt2),
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
        val expected = Expressions.coalesce(
            Paths.path(TestTable::nullableInt1),
            listOf(
                Expressions.value(nullableInt1),
                Expressions.value(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
