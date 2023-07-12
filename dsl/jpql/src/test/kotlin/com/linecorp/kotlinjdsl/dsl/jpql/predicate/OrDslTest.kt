package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class OrDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `or predicate predicate`() {
        // when
        val actual = testJpql {
            or(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Or(
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
    }

    @Test
    fun `or collection predicate predicate`() {
        // when
        val actual = testJpql {
            or(
                listOf(
                    path(TestTable::int1).equal(int1),
                    path(TestTable::int1).equal(int2),
                ),
            )
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Or(
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
    }

    @Test
    fun `predicate or predicate`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).equal(int1).or(path(TestTable::int1).equal(int2))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Or(
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
    }

    private class TestTable {
        val int1: Int = 1
    }
}
