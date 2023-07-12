package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class WhereDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `where predicate`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).where(
                path(TestTable::int1).equal(int1),
            )
        }.toQuery()

        val actual: SelectQuery<TestTable> = select // for type check

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            ),
            where = Equal(
                left = Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                right = Value(int1),
            ),
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `where and predicate`() {
        // when
        val select1 = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).whereAnd(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toQuery()

        val actual1: SelectQuery<TestTable> = select1 // for type check

        val select2 = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).whereAnd(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual2: SelectQuery<TestTable> = select2 // for type check

        val select3 = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).where(
                and(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual3: SelectQuery<TestTable> = select3 // for type check

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            ),
            where = And(
                listOf(
                    Equal(
                        left = Field(
                            Int::class,
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::int1.name,
                        ),
                        right = Value(int1),
                    ),
                    Equal(
                        left = Field(
                            Int::class,
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::int1.name,
                        ),
                        right = Value(int2),
                    ),
                ),
            ),
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
        assertThat(actual3).isEqualTo(expected)
    }

    @Test
    fun `where or predicate`() {
        // when
        val select1 = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).whereOr(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toQuery()

        val actual1: SelectQuery<TestTable> = select1 // for type check

        val select2 = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).whereOr(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual2: SelectQuery<TestTable> = select2 // for type check

        val select3 = testJpql {
            select(
                entity(TestTable::class),
            ).from(
                entity(TestTable::class),
            ).where(
                or(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual3: SelectQuery<TestTable> = select3 // for type check

        // then
        val expected = JpqlSelectQuery<TestTable>(
            returnType = TestTable::class,
            select = listOf(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!)),
            distinct = false,
            from = listOf(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            ),
            where = Or(
                listOf(
                    Equal(
                        left = Field(
                            Int::class,
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::int1.name,
                        ),
                        right = Value(int1),
                    ),
                    Equal(
                        left = Field(
                            Int::class,
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::int1.name,
                        ),
                        right = Value(int2),
                    ),
                ),
            ),
            groupBy = null,
            having = null,
            orderBy = null,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
        assertThat(actual3).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
    }
}
