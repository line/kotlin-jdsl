package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlAliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.*
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PredicatesTest : WithAssertions {
    private val int1: Int = 1
    private val int2: Int = 2

    @Test
    fun `not predicate`() {
        // when
        val actual = Predicates.not(
            Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlNot(
                JpqlEqual(
                    value = JpqlField<Int>(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::int1.name,
                    ),
                    compareValue = JpqlValue(int1),
                ),
            ),
        )
    }

    @Test
    fun `and predicate predicate`() {
        // when
        val actual = Predicates.and(
            listOf(
                Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int2)),
            ),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int2),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `or predicate predicate`() {
        // when
        val actual = Predicates.or(
            listOf(
                Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int2)),
            ),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlOr(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int2),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `equal expression expression`() {
        // when
        val actual = Predicates.equal(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlEqual(
                value = JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                compareValue = JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
            ),
        )
    }

    @Test
    fun `equal nullable expression expression`() {
        // when
        val actual = Predicates.equal(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int1),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlEqual(
                value = JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                compareValue = JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
        )
    }

    @Test
    fun `equal nullable expression nullable expression`() {
        // when
        val actual = Predicates.equal(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlEqual(
                value = JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                compareValue = JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
            ),
        )
    }

    @Test
    fun `notEqual expression expression`() {
        // when
        val actual = Predicates.notEqual(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlNotEqual(
                value = JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                compareValue = JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
            ),
        )
    }

    @Test
    fun `notEqual nullable expression expression`() {
        // when
        val actual = Predicates.notEqual(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int1),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlNotEqual(
                value = JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                compareValue = JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
        )
    }

    @Test
    fun `notEqual nullable expression nullable expression`() {
        // when
        val actual = Predicates.notEqual(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
        ).toPredicate()

        // then
        assertThat(actual).isEqualTo(
            JpqlNotEqual(
                value = JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                compareValue = JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
            ),
        )
    }

    @Test
    fun `greaterThan expression expression`() {
        // when
        val actual = Predicates.greaterThan(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThan(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValue = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThan expression nullable expression`() {
        // when
        val actual = Predicates.greaterThan(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::nullableInt2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThan(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValue = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThan nullable expression expression`() {
        // when
        val actual = Predicates.greaterThan(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThan(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValue = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThan nullable expression nullable expression`() {
        // when
        val actual = Predicates.greaterThan(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThan(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValue = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanOrEqualTo expression expression`() {
        // when
        val actual = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThanOrEqualTo(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValue = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanOrEqualTo expression nullable expression`() {
        // when
        val actual = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::nullableInt2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThanOrEqualTo(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValue = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanOrEqualTo nullable expression expression`() {
        // when
        val actual = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThanOrEqualTo(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValue = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanOrEqualTo nullable expression nullable expression`() {
        // when
        val actual = Predicates.greaterThanOrEqualTo(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
        ).toPredicate()

        // then
        val expected = JpqlGreaterThanOrEqualTo(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValue = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
        val int2: Int = 1
        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
    }
}
