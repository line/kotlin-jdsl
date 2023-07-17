package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Queries
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import org.junit.jupiter.api.Test

class SetDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 2

    private val nullableInt1: Int? = null
    private val nullableInt2: Int? = null

    @Test
    fun `set path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::int1),
                    Expressions.value(int1),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set nullable path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::nullableInt1) to int1,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(int1),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set nullable path to nullable value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::nullableInt1) to nullableInt1,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set path to value path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::int1) to int1,
                path(TestTable::int2) to int2,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::int1),
                    Expressions.value(int1),
                ),
                Paths.pair(
                    Paths.path(TestTable::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set nullable path to value path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::nullableInt1) to int1,
                path(TestTable::int2) to int2,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(int1),
                ),
                Paths.pair(
                    Paths.path(TestTable::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set nullable path to nullable value path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::nullableInt1) to nullableInt1,
                path(TestTable::int2) to int2,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
                Paths.pair(
                    Paths.path(TestTable::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set nullable path to nullable value nullable path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::nullableInt1) to nullableInt1,
                path(TestTable::nullableInt2) to int2,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
                Paths.pair(
                    Paths.path(TestTable::nullableInt2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set nullable path to nullable value nullable path to nullable value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                path(TestTable::nullableInt1) to nullableInt1,
                path(TestTable::nullableInt2) to nullableInt2,
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
                Paths.pair(
                    Paths.path(TestTable::nullableInt2),
                    Expressions.value(nullableInt2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set collection path to value path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                listOf(
                    path(TestTable::int1) to int1,
                    path(TestTable::int2) to int2,
                ),
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::int1),
                    Expressions.value(int1),
                ),
                Paths.pair(
                    Paths.path(TestTable::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set collection nullable path to value path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                listOf(
                    path(TestTable::nullableInt1) to int1,
                    path(TestTable::int2) to int2,
                ),
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(int1),
                ),
                Paths.pair(
                    Paths.path(TestTable::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set collection nullable path to nullable value path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                listOf(
                    path(TestTable::nullableInt1) to nullableInt1,
                    path(TestTable::int2) to int2,
                ),
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
                Paths.pair(
                    Paths.path(TestTable::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set collection nullable path to nullable value nullable path to value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                listOf(
                    path(TestTable::nullableInt1) to nullableInt1,
                    path(TestTable::nullableInt2) to int2,
                ),
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
                Paths.pair(
                    Paths.path(TestTable::nullableInt2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `set collection nullable path to nullable value nullable path to nullable value`() {
        // when
        val update = testJpql {
            update(
                entity(TestTable::class),
            ).set(
                listOf(
                    path(TestTable::nullableInt1) to nullableInt1,
                    path(TestTable::nullableInt2) to nullableInt2,
                ),
            )
        }.toQuery()

        val actual: UpdateQuery<TestTable> = update // for type check

        // then
        val expected = Queries.update(
            entity = Paths.entity(TestTable::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable::nullableInt1),
                    Expressions.value(nullableInt1),
                ),
                Paths.pair(
                    Paths.path(TestTable::nullableInt2),
                    Expressions.value(nullableInt2),
                ),
            ),
            where = null,
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
