package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicates
import org.junit.jupiter.api.Test

class BetweenDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `expression between value and value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).between(int1, int2)
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
            Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression between expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).between(path(TestTable::int2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression between nullable expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).between(path(TestTable::nullableInt2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression between expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).between(path(TestTable::int2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression between nullable expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).between(path(TestTable::nullableInt2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression between value and value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).between(int1, int2)
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
            Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression between expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).between(path(TestTable::int2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression between nullable expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).between(path(TestTable::nullableInt2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression between expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).between(path(TestTable::int2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression between nullable expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).between(path(TestTable::nullableInt2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.between(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression notBetween value and value`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notBetween(int1, int2)
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::int1),
            Expressions.value(int1),
            Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression notBetween expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notBetween(path(TestTable::int2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression notBetween nullable expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notBetween(path(TestTable::nullableInt2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression notBetween expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notBetween(path(TestTable::int2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression notBetween nullable expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).notBetween(path(TestTable::nullableInt2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::int1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression notBetween value and value`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notBetween(int1, int2)
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::nullableInt1),
            Expressions.value(int1),
            Expressions.value(int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression notBetween expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notBetween(path(TestTable::int2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression notBetween nullable expression and expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notBetween(path(TestTable::nullableInt2), path(TestTable::int3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::int3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression notBetween expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notBetween(path(TestTable::int2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::int2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression notBetween nullable expression and nullable expression`() {
        // when
        val actual = testJpql {
            path(TestTable::nullableInt1).notBetween(path(TestTable::nullableInt2), path(TestTable::nullableInt3))
        }.toPredicate()

        // then
        val expected = Predicates.notBetween(
            Paths.path(TestTable::nullableInt1),
            Paths.path(TestTable::nullableInt2),
            Paths.path(TestTable::nullableInt3),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val int2: Int = 2
        val int3: Int = 3
        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
        val nullableInt3: Int? = null
    }
}
