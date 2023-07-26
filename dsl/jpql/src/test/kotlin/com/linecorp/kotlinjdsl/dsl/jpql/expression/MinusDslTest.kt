package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class MinusDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val nullableInt1: Int? = null

    @Test
    fun `int expression minus int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::int1).minus(int1)
        }

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.minus(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `int expression minus nullable int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::int1).minus(nullableInt1)
        }

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.minus(
            Paths.path(TestTable1::int1),
            Expressions.value(nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `int expression minus int expression`() {
        // when
        val expression = testJpql {
            path(TestTable1::int1).minus(path(TestTable1::int2))
        }

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.minus(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `number expression minus int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::number1).minus(int1)
        }

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.minus(
            Paths.path(TestTable1::number1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `number expression minus nullable int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::number1).minus(nullableInt1)
        }

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.minus(
            Paths.path(TestTable1::number1),
            Expressions.value(nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `number expression minus int expression`() {
        // when
        val expression = testJpql {
            path(TestTable1::number1).minus(path(TestTable1::int2))
        }

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.minus(
            Paths.path(TestTable1::number1),
            Paths.path(TestTable1::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val number1: Number = 1

        val int1: Int = 1
        val int2: Int = 2
    }
}
