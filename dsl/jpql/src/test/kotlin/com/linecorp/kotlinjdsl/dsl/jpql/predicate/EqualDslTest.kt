package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class EqualDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1

    private val nullableInt1: Int? = null

    @Test
    fun `expression equal value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).equal(int1)
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                right = Param(null, int1),
                not = false,
            ),
        )
    }

    @Test
    fun `expression equal expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).equal(path(TestTable::int2))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal<Int>(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                right = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
                not = false,
            ),
        )
    }

    @Test
    fun `nullable expression equal value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).equal(int1)
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Param(null, int1),
                not = false,
            ),
        )
    }

    @Test
    fun `nullable expression equal nullable value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).equal(nullableInt1)
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Param(null, nullableInt1),
                not = false,
            ),
        )
    }

    @Test
    fun `nullable expression equal expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).equal(path(TestTable::int1))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal<Int?>(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                not = false,
            ),
        )
    }

    @Test
    fun `nullable expression equal nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).equal(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal<Int>(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
                not = false,
            ),
        )
    }

    @Test
    fun `expression not equal value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notEqual(int1)
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                right = Param(null, int1),
                not = true,
            ),
        )
    }

    @Test
    fun `expression not equal expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notEqual(path(TestTable::int2))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal<Int>(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                right = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
                not = true,
            ),
        )
    }

    @Test
    fun `nullable expression not equal value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notEqual(int1)
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Param(null, int1),
                not = true,
            ),
        )
    }

    @Test
    fun `nullable expression not equal nullable value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notEqual(nullableInt1)
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Param(null, nullableInt1),
                not = true,
            ),
        )
    }

    @Test
    fun `nullable expression not equal expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notEqual(path(TestTable::int1))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal<Int?>(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                not = true,
            ),
        )
    }

    @Test
    fun `nullable expression not equal nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notEqual(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Equal<Int>(
                left = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                right = Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
                not = true,
            ),
        )
    }

    private class TestTable {
        val int1: Int = 1
        val int2: Int = 1

        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
    }
}
