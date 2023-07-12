package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class OrderByDslTest : AbstractJpqlDslTest() {
    @Test
    fun `orderBy expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                path(TestTable::int1).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy nullable expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                path(TestTable::nullableInt1).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy expression asc expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                path(TestTable::int1).asc(),
                path(TestTable::int2).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int2.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy expression asc nullable expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                path(TestTable::int1).asc(),
                path(TestTable::nullableInt1).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy nullable expression asc nullable expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                path(TestTable::nullableInt1).asc(),
                path(TestTable::nullableInt2).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    Sort.Order.ASC,
                ),
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt2.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy collection expression asc expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                listOf(
                    path(TestTable::int1).asc(),
                    path(TestTable::int2).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int2.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy collection expression asc nullable expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                listOf(
                    path(TestTable::int1).asc(),
                    path(TestTable::nullableInt1).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `orderBy collection nullable expression asc nullable expression asc`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).orderBy(
                listOf(
                    path(TestTable::nullableInt1).asc(),
                    path(TestTable::nullableInt2).asc(),
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
            groupBy = null,
            having = null,
            orderBy = listOf(
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    Sort.Order.ASC,
                ),
                SortExpression(
                    Field<Int?>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt2.name,
                    ),
                    Sort.Order.ASC,
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
}
