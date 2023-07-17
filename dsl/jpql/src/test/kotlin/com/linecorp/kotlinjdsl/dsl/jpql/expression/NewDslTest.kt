package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class NewDslTest : AbstractJpqlDslTest() {
    @Test
    fun `new type expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type nullable expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type expression expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::int1), path(TestTable::int2))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type expression nullable expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::int1), path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type nullable expression nullable expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::nullableInt1), path(TestTable::nullableInt2))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type collection expression expression`() {
        // when
        val expression = testJpql {
            new(Row::class, listOf(path(TestTable::int1), path(TestTable::int2)))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type collection expression nullable expression`() {
        // when
        val expression = testJpql {
            new(Row::class, listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type collection nullable expression nullable expression`() {
        // when
        val expression = testJpql {
            new(Row::class, listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val int2: Int = 1

        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
    }

    private class Row
}
