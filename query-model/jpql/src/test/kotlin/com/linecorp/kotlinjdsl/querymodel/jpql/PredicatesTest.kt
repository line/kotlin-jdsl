package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlAliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlAnd
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlOr
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PredicatesTest : WithAssertions {
    private val int1: Int = 1
    private val int2: Int = 2

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
                        left = JpqlField(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        right = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        left = JpqlField(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        right = JpqlValue(int2),
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
                        left = JpqlField(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        right = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        left = JpqlField(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        right = JpqlValue(int2),
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
            JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
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
            JpqlEqual<Int?>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                right = JpqlField(
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
            JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                right = JpqlField(
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
            JpqlNotEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
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
            JpqlNotEqual<Int?>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                right = JpqlField(
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
            JpqlNotEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
            ),
        )
    }

    private class TestTable1 {
        val int1: Int = 1
        val int2: Int = 1
        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
    }
}
