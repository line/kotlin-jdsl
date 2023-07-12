package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.New
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
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
        val expected = New(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
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
