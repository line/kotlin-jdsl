package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.ExpressionAndExpressionImpl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.PathAndExpressionImpl
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class ToDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1

    private val long1: Long = 1

    private val nullableInt1: Int? = null

    @Test
    fun `path to value`() {
        // when
        val expression = testJpql {
            path(TestTable::int1) to int1
        }

        val actual: PathAndExpression<Int> = expression

        // then
        assertThat(actual).isEqualTo(
            PathAndExpressionImpl(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(int1),
            ),
        )
    }

    @Test
    fun `nullable path to value`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to int1
        }

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            PathAndExpressionImpl(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(int1),
            ),
        )
    }

    @Test
    fun `nullable path to nullable value`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to nullableInt1
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            PathAndExpressionImpl(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(nullableInt1),
            ),
        )
    }

    @Test
    fun `expression to value`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)) to long1
        }

        val actual: ExpressionAndExpression<Long> = expression

        // then
        assertThat(actual).isEqualTo(
            ExpressionAndExpressionImpl(
                Count(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    false,
                ),
                Value(long1),
            ),
        )
    }

    @Test
    fun `nullable expression to value`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to int1
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            ExpressionAndExpressionImpl(
                Max(
                    Field(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    false,
                ),
                Value(int1),
            ),
        )
    }

    @Test
    fun `nullable expression to nullable value`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to nullableInt1
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            ExpressionAndExpressionImpl(
                Max(
                    Field(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    false,
                ),
                Value(nullableInt1),
            ),
        )
    }

    @Test
    fun `path to expression`() {
        // when
        val expression = testJpql {
            path(TestTable::int1) to value(int1)
        }

        val actual: PathAndExpression<Int> = expression

        // then
        assertThat(actual).isEqualTo(
            PathAndExpressionImpl(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Value(int1),
            ),
        )
    }

    @Test
    fun `nullable path to expression`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to value(int1)
        }

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            PathAndExpressionImpl(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(int1),
            ),
        )
    }

    @Test
    fun `nullable path to nullable expression`() {
        // when
        val expression = testJpql {
            path(TestTable::nullableInt1) to value(nullableInt1)
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            PathAndExpressionImpl(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Value(nullableInt1),
            ),
        )
    }

    @Test
    fun `expression to expression`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)) to value(long1)
        }

        val actual: ExpressionAndExpression<Long> = expression

        // then
        assertThat(actual).isEqualTo(
            ExpressionAndExpressionImpl(
                Count(
                    Field<Int>(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                    false,
                ),
                Value(long1),
            ),
        )
    }

    @Test
    fun `nullable expression to expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to value(int1)
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            ExpressionAndExpressionImpl(
                Max(
                    Field(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    false,
                ),
                Value(int1),
            ),
        )
    }

    @Test
    fun `nullable expression to nullable expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)) to value(nullableInt1)
        }

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            ExpressionAndExpressionImpl(
                Max(
                    Field(
                        Int::class,
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                    false,
                ),
                Value(nullableInt1),
            ),
        )
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
