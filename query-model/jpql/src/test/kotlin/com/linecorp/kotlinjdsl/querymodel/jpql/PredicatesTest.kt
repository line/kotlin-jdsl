package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlAliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
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

    @Test
    fun `between expression expression and expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between expression nullable expression and expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between expression expression and nullable expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between expression nullable expression and nullable expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between nullable expression expression and expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between nullable expression nullable expression and expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between nullable expression expression and nullable expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `between nullable expression nullable expression and nullable expression`() {
        // when
        val actual = Predicates.between(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween expression expression and expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween expression nullable expression and expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween expression expression and nullable expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween expression nullable expression and nullable expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween nullable expression expression and expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween nullable expression nullable expression and expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::int3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween nullable expression expression and nullable expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::int2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notBetween nullable expression nullable expression and nullable expression`() {
        // when
        val actual = Predicates.notBetween(
            Paths.path(TestTable1::nullableInt1),
            Paths.path(TestTable1::nullableInt2),
            Paths.path(TestTable1::nullableInt3),
        ).toPredicate()

        // then
        val expected = JpqlNotBetween(
            value = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            min = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt2.name,
            ),
            max = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt3.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in expression expression expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in expression expression nullable expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in expression nullable expression expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in expression nullable expression nullable expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in nullable expression expression expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in nullable expression expression nullable expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in nullable expression nullable expression expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in nullable expression nullable expression nullable expression`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn expression expression expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn expression expression nullable expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn expression nullable expression expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn expression nullable expression nullable expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::int1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn nullable expression expression expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn nullable expression expression nullable expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::int2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn nullable expression nullable expression expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::int3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn nullable expression nullable expression nullable expression`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::nullableInt1),
            listOf(
                Paths.path(TestTable1::nullableInt2),
                Paths.path(TestTable1::nullableInt3),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotIn<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValues = listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt3.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in expression subquery`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::int1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::int2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlInSubquery<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in expression nullable subquery`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::int1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::nullableInt2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlInSubquery<Int?>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableInt2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in nullable expression subquery`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::nullableInt1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::int2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlInSubquery<Int?>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in nullable expression nullable subquery`() {
        // when
        val actual = Predicates.`in`(
            Paths.path(TestTable1::nullableInt1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::nullableInt2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlInSubquery<Int?>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableInt2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn expression subquery`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::int1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::int2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotInSubquery<Int>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn expression nullable subquery`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::int1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::nullableInt2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotInSubquery<Int?>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableInt2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn nullable expression subquery`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::nullableInt1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::int2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotInSubquery<Int?>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn nullable expression nullable subquery`() {
        // when
        val actual = Predicates.notIn(
            Paths.path(TestTable1::nullableInt1),
            Expressions.subquery(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::nullableInt2)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotInSubquery<Int?>(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            subquery = JpqlSubquery(
                JpqlSelectQuery(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableInt2.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `like expression expression`() {
        // when
        val actual = Predicates.like(
            Paths.path(TestTable1::string1),
            Paths.path(TestTable1::string2),
        ).toPredicate()

        // then
        val expected = JpqlLike<String>(
            value = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            pattern = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `like nullable expression expression`() {
        // when
        val actual = Predicates.like(
            Paths.path(TestTable1::nullableString1),
            Paths.path(TestTable1::string2),
        ).toPredicate()

        // then
        val expected = JpqlLike(
            value = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableString1.name,
            ),
            pattern = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notLike expression expression`() {
        // when
        val actual = Predicates.notLike(
            Paths.path(TestTable1::string1),
            Paths.path(TestTable1::string2),
        ).toPredicate()

        // then
        val expected = JpqlNotLike<String>(
            value = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            pattern = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notLike nullable expression expression`() {
        // when
        val actual = Predicates.notLike(
            Paths.path(TestTable1::nullableString1),
            Paths.path(TestTable1::string2),
        ).toPredicate()

        // then
        val expected = JpqlNotLike(
            value = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableString1.name,
            ),
            pattern = JpqlField(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string2.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `isEmpty expression`() {
        // when
        val actual = Predicates.isEmpty(
            Paths.path(TestTable1::ints1),
        ).toPredicate()

        // then
        val expected = JpqlIsEmpty(
            JpqlField<List<Int>>(
                List::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::ints1.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `isEmpty nullable expression`() {
        // when
        val actual = Predicates.isEmpty(
            Paths.path(TestTable1::nullableInts1),
        ).toPredicate()

        // then
        val expected = JpqlIsEmpty(
            JpqlField<List<Int?>>(
                List::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInts1.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `isNotEmpty expression`() {
        // when
        val actual = Predicates.isNotEmpty(
            Paths.path(TestTable1::ints1),
        ).toPredicate()

        // then
        val expected = JpqlIsNotEmpty(
            JpqlField<List<Int>>(
                List::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::ints1.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `isNotEmpty nullable expression`() {
        // when
        val actual = Predicates.isNotEmpty(
            Paths.path(TestTable1::nullableInts1),
        ).toPredicate()

        // then
        val expected = JpqlIsNotEmpty(
            JpqlField<List<Int?>>(
                List::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInts1.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `exists subquery`() {
        // when
        val actual = Predicates.exists(
            Expressions.subquery<Int>(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::int1)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlExists(
            JpqlSubquery(
                JpqlSelectQuery<Int>(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `exists nullable subquery`() {
        // when
        val actual = Predicates.exists(
            Expressions.subquery<Int?>(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::nullableInt1)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlExists(
            JpqlSubquery(
                JpqlSelectQuery<Int?>(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableInt1.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notExists subquery`() {
        // when
        val actual = Predicates.notExists(
            Expressions.subquery<Int>(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::int1)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotExists(
            JpqlSubquery(
                JpqlSelectQuery<Int>(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notExists nullable subquery`() {
        // when
        val actual = Predicates.notExists(
            Expressions.subquery<Int?>(
                Queries.select(
                    returnType = Int::class,
                    select = listOf(Paths.path(TestTable1::nullableInt1)),
                    distinct = false,
                    from = listOf(Paths.entity(TestTable1::class)),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        ).toPredicate()

        // then
        val expected = JpqlNotExists(
            JpqlSubquery(
                JpqlSelectQuery<Int?>(
                    returnType = Int::class,
                    select = listOf(
                        JpqlField<Int?>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::nullableInt1.name,
                        ),
                    ),
                    distinct = false,
                    from = listOf(
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    ),
                    where = null,
                    groupBy = null,
                    having = null,
                    orderBy = null,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val string1: String = "string1"
        val string2: String = "string1"
        val nullableString1: String? = null

        val int1: Int = 1
        val int2: Int = 1
        val int3: Int = 1
        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
        val nullableInt3: Int? = null

        val ints1: List<Int> = listOf(1, 2, 3)
        val nullableInts1: List<Int?> = listOf(null, null, null)
    }
}
