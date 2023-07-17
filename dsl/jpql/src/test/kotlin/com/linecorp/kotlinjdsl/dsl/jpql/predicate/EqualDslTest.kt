package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicates
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
            Predicates.equal(
                Paths.path(TestTable::int1),
                Expressions.value(int1),
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
            Predicates.equal(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::int2),
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
            Predicates.equal(
                Paths.path(TestTable::nullableInt1),
                Expressions.value(int1),
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
            Predicates.equal(
                Paths.path(TestTable::nullableInt1),
                Expressions.value(nullableInt1),
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
            Predicates.equal(
                Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::int1),
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
            Predicates.equal(
                Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::nullableInt2),
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
            Predicates.notEqual(
                Paths.path(TestTable::int1),
                Expressions.value(int1),
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
            Predicates.notEqual(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::int2),
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
            Predicates.notEqual(
                Paths.path(TestTable::nullableInt1),
                Expressions.value(int1),
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
            Predicates.notEqual(
                Paths.path(TestTable::nullableInt1),
                Expressions.value(nullableInt1),
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
            Predicates.notEqual(
                Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::int1),
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
            Predicates.notEqual(
                Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::nullableInt2),
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
