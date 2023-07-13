package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class WhereDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `where predicate`() {
        // when
        val update = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).where(
                path(TestTable::int1).equal(int1),
            )
        }.toQuery()

        val actual: DeleteQuery<TestTable> = update // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            where = Equal(
                left = Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                right = Value(int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `where and predicate`() {
        // when
        val update1 = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).whereAnd(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toQuery()

        val actual1: DeleteQuery<TestTable> = update1 // for type check

        val update2 = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).whereAnd(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual2: DeleteQuery<TestTable> = update2 // for type check

        val update3 = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).where(
                and(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual3: DeleteQuery<TestTable> = update3 // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
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
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
        assertThat(actual3).isEqualTo(expected)
    }

    @Test
    fun `where or predicate`() {
        // when
        val update1 = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).whereOr(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toQuery()

        val actual1: DeleteQuery<TestTable> = update1 // for type check

        val update2 = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).whereOr(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual2: DeleteQuery<TestTable> = update2 // for type check

        val update3 = testJpql {
            deleteFrom(
                entity(TestTable::class),
            ).where(
                or(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual3: DeleteQuery<TestTable> = update3 // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
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
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
        assertThat(actual3).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
    }
}
