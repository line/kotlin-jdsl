package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class DivDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val nullableInt1: Int? = null

    @Test
    fun `int expression div int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::int1).div(int1)
        }

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.div(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `int expression div nullable int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::int1).div(nullableInt1)
        }

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.div(
            Paths.path(TestTable1::int1),
            Expressions.value(nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `int expression div int expression`() {
        // when
        val expression = testJpql {
            path(TestTable1::int1).div(path(TestTable1::int2))
        }

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.div(
            Paths.path(TestTable1::int1),
            Paths.path(TestTable1::int2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `number expression div int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::number1).div(int1)
        }

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.div(
            Paths.path(TestTable1::number1),
            Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `number expression div nullable int value`() {
        // when
        val expression = testJpql {
            path(TestTable1::number1).div(nullableInt1)
        }

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.div(
            Paths.path(TestTable1::number1),
            Expressions.value(nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `number expression div int expression`() {
        // when
        val expression = testJpql {
            path(TestTable1::number1).div(path(TestTable1::int2))
        }

        val actual: Expression<Number> = expression // for type check

        // then
        val expected = Expressions.div(
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
