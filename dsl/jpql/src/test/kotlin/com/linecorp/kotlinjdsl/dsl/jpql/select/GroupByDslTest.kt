package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.JpqlSelectQuery
import org.junit.jupiter.api.Test

class GroupByDslTest : AbstractJpqlDslTest() {
    @Test
    fun `groupBy expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                path(TestTable::int1),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
            ),
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy nullable expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                path(TestTable::nullableInt1),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf(
                Field<Int?>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy expression expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                path(TestTable::int1),
                path(TestTable::int2),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf<Path<Any?>>(
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
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy expression nullable expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                path(TestTable::int1),
                path(TestTable::nullableInt1),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf<Path<Any?>>(
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
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy nullable expression nullable expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                path(TestTable::nullableInt1),
                path(TestTable::nullableInt2),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf<Path<Any?>>(
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
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy collection expression expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                listOf(
                    path(TestTable::int1),
                    path(TestTable::int2),
                ),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf<Path<Any?>>(
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
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy collection expression nullable expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                listOf(
                    path(TestTable::int1),
                    path(TestTable::nullableInt1),
                ),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf<Path<Any?>>(
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
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `groupBy collection nullable expression nullable expression`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).groupBy(
                listOf(
                    path(TestTable::nullableInt1),
                    path(TestTable::nullableInt2),
                ),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            where = null,
            groupBy = listOf<Path<Any?>>(
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
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val int2: Int = 1

        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
    }
}
