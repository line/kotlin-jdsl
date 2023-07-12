package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class FromDslTest : AbstractJpqlDslTest() {
    @Test
    fun `from entity`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from entity entity`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class),
                entity(TestTable2::class),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                AliasedPath(Entity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from collection entity entity`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                listOf(
                    entity(TestTable1::class),
                    entity(TestTable2::class),
                ),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                AliasedPath(Entity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from entity join path`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class).join(path(TestTable1::table1)),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                Join(
                    left = AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                    right = AliasedPath(
                        Field<OtherTable>(
                            OtherTable::class,
                            AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::table1.name,
                        ),
                        OtherTable::class.simpleName!!,
                    ),
                    on = null,
                    joinType = JoinType.INNER,
                    fetch = false,
                ),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from entity join nullable path`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class).join(path(TestTable1::nullableTable1)),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                Join(
                    left = AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                    right = AliasedPath(
                        Field<OtherTable>(
                            OtherTable::class,
                            AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableTable1.name,
                        ),
                        OtherTable::class.simpleName!!,
                    ),
                    on = null,
                    joinType = JoinType.INNER,
                    fetch = false,
                ),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from entity entity join path`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class),
                entity(TestTable2::class).join(path(TestTable2::table1)),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                Join(
                    left = AliasedPath(Entity(TestTable2::class), TestTable2::class.simpleName!!),
                    right = AliasedPath(
                        Field<OtherTable>(
                            OtherTable::class,
                            AliasedPath(Entity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::table1.name,
                        ),
                        OtherTable::class.simpleName!!,
                    ),
                    on = null,
                    joinType = JoinType.INNER,
                    fetch = false,
                ),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from entity entity join nullable path`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class),
                entity(TestTable2::class).join(path(TestTable2::nullableTable1)),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable1> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable1::class), TestTable1::class.simpleName!!),
                Join(
                    left = AliasedPath(Entity(TestTable2::class), TestTable2::class.simpleName!!),
                    right = AliasedPath(
                        Field<OtherTable>(
                            OtherTable::class,
                            AliasedPath(Entity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::nullableTable1.name,
                        ),
                        OtherTable::class.simpleName!!,
                    ),
                    on = null,
                    joinType = JoinType.INNER,
                    fetch = false,
                ),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val table1: OtherTable = OtherTable()

        val nullableTable1: OtherTable? = null
    }

    private class TestTable2 {
        val table1: OtherTable = OtherTable()

        val nullableTable1: OtherTable? = null
    }

    private class OtherTable
}
