package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.junit.jupiter.api.Test

class LessThanDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `expression lessThan value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(path(TestTable::nullableInt1))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(path(TestTable::nullableInt1), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThan nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThan(path(TestTable::nullableInt1), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(path(TestTable::nullableInt2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThan nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThan(path(TestTable::nullableInt2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(path(TestTable::nullableInt2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lt nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lt(path(TestTable::nullableInt2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(path(TestTable::nullableInt2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lt nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lt(path(TestTable::nullableInt2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThanOrEqualTo value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThanOrEqualTo(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThanOrEqualTo expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThanOrEqualTo(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression lessThanOrEqualTo nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).lessThanOrEqualTo(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThanOrEqualTo value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThanOrEqualTo(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThanOrEqualTo expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThanOrEqualTo(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression lessThanOrEqualTo nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).lessThanOrEqualTo(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression le value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).le(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression ge expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).le(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression ge nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).le(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression ge value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).le(int1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression ge expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).le(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression ge nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).le(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
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
