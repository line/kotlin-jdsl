package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.junit.jupiter.api.Test

class LiteralDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val long1: Long = 1
    private val float1: Float = 1f
    private val double1: Double = 1.0
    private val boolean1: Boolean = true
    private val char1: Char = 'a'
    private val string1: String = "string1"

    private enum class TestEnum {
        TEST
    }

    @Test
    fun intLiteral() {
        // when
        val expression = testJpql {
            intLiteral(int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.intLiteral(int1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun longLiteral() {
        // when
        val expression = testJpql {
            longLiteral(long1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.longLiteral(long1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun floatLiteral() {
        // when
        val expression = testJpql {
            floatLiteral(float1)
        }.toExpression()

        val actual: Expression<Float> = expression // for type check

        // then
        val expected = Expressions.floatLiteral(float1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun doubleLiteral() {
        // when
        val expression = testJpql {
            doubleLiteral(double1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.doubleLiteral(double1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun booleanLiteral() {
        // when
        val expression = testJpql {
            booleanLiteral(boolean1)
        }.toExpression()

        val actual: Expression<Boolean> = expression // for type check

        // then
        val expected = Expressions.booleanLiteral(boolean1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun charLiteral() {
        // when
        val expression = testJpql {
            charLiteral(char1)
        }.toExpression()

        val actual: Expression<Char> = expression // for type check

        // then
        val expected = Expressions.charLiteral(char1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun stringLiteral() {
        // when
        val expression = testJpql {
            stringLiteral(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.stringLiteral(string1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun enumLiteral() {
        // when
        val expression = testJpql {
            enumLiteral(TestEnum.TEST)
        }.toExpression()

        val actual: Expression<TestEnum> = expression // for type check

        // then
        val expected = Expressions.enumLiteral(TestEnum.TEST)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun nullLiteral() {
        // when
        val expression = testJpql {
            nullLiteral<Int>()
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.nullLiteral<Int>()

        assertThat(actual).isEqualTo(expected)
    }
}
