package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicates
import org.junit.jupiter.api.Test

class GreaterThanDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `expression greaterThan value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(path(TestTable::nullableInt1))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(path(TestTable::nullableInt1), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThan nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThan(path(TestTable::nullableInt1), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(path(TestTable::nullableInt2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThan nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThan(path(TestTable::nullableInt2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(path(TestTable::nullableInt2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression gt nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).gt(path(TestTable::nullableInt2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt value inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(int1, inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt value inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(int1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(path(TestTable::int2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(path(TestTable::int2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt nullable expression inclusive false`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(path(TestTable::nullableInt2), inclusive = false)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression gt nullable expression inclusive true`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).gt(path(TestTable::nullableInt2), inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThanOrEqualTo value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThanOrEqualTo(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThanOrEqualTo expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThanOrEqualTo(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression greaterThanOrEqualTo nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).greaterThanOrEqualTo(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThanOrEqualTo value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThanOrEqualTo(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThanOrEqualTo expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThanOrEqualTo(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression greaterThanOrEqualTo nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).greaterThanOrEqualTo(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression ge value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).ge(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression ge expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).ge(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression ge nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).ge(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression ge value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).ge(int1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression ge expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).ge(path(TestTable::int2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression ge nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).ge(path(TestTable::nullableInt2))
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
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
