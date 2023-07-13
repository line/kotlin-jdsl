package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.UpdateQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class WhereDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `where predicate`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).where(
                path(TestTable::int1).equal(int1),
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = JpqlUpdateQuery(
            entity = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            set = mapOf(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ) to Value(int1),
            ),
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
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).whereAnd(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toQuery()

        val actual1: UpdateQuery<TestTable> = update1 // for type check

        val update2 = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).whereAnd(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual2: UpdateQuery<TestTable> = update2 // for type check

        val update3 = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).where(
                and(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual3: UpdateQuery<TestTable> = update3 // for type check

        // then
        val expected = JpqlUpdateQuery(
            entity = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            set = mapOf(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ) to Value(int1),
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
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
        assertThat(actual3).isEqualTo(expected)
    }

    @Test
    fun `where or predicate`() {
        // when
        val update1 = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).whereOr(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toQuery()

        val actual1: UpdateQuery<TestTable> = update1 // for type check

        val update2 = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).whereOr(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual2: UpdateQuery<TestTable> = update2 // for type check

        val update3 = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            ).where(
                or(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toQuery()

        val actual3: UpdateQuery<TestTable> = update3 // for type check

        // then
        val expected = JpqlUpdateQuery(
            entity = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            set = mapOf(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ) to Value(int1),
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
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
        assertThat(actual3).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
    }
}
