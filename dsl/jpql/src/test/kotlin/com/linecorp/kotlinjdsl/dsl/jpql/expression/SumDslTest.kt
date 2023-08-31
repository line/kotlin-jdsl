package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import java.math.BigDecimal
import java.math.BigInteger
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SumDslTest : WithAssertions {
    private class Class1 {
        val int1: Int = 100
        val long1: Long = 100L
        val float1: Float = 100f
        val double1: Double = 100.0
        val bigInteger1: BigInteger = BigInteger.valueOf(100)
        val bigDecimal1: BigDecimal = BigDecimal.valueOf(100)
    }

    private val intExpression1 = Expressions.value(100)
    private val longExpression1 = Expressions.value(100L)
    private val floatExpression1 = Expressions.value(100f)
    private val doubleExpression1 = Expressions.value(100.0)
    private val bigIntegerExpression1 = Expressions.value(BigInteger.valueOf(100))
    private val bigDecimalExpression1 = Expressions.value(BigDecimal.valueOf(100))

    @Test
    fun `sum() with a int property`() {
        // when
        val expression = queryPart {
            sum(Class1::int1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = Paths.path(Class1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a int expression`() {
        // when
        val expression = queryPart {
            sum(intExpression1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a int property`() {
        // when
        val expression = queryPart {
            sumDistinct(Class1::int1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = Paths.path(Class1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a int expression`() {
        // when
        val expression = queryPart {
            sumDistinct(intExpression1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a long property`() {
        // when
        val expression = queryPart {
            sum(Class1::long1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = Paths.path(Class1::long1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a long expression`() {
        // when
        val expression = queryPart {
            sum(longExpression1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = longExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a long property`() {
        // when
        val expression = queryPart {
            sumDistinct(Class1::long1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = Paths.path(Class1::long1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a long expression`() {
        // when
        val expression = queryPart {
            sumDistinct(longExpression1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = longExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a float property`() {
        // when
        val expression = queryPart {
            sum(Class1::float1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = Paths.path(Class1::float1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a float expression`() {
        // when
        val expression = queryPart {
            sum(floatExpression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = floatExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a float property`() {
        // when
        val expression = queryPart {
            sumDistinct(Class1::float1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = Paths.path(Class1::float1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a float expression`() {
        // when
        val expression = queryPart {
            sumDistinct(floatExpression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = floatExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a double property`() {
        // when
        val expression = queryPart {
            sum(Class1::double1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = Paths.path(Class1::double1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a double expression`() {
        // when
        val expression = queryPart {
            sum(doubleExpression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = doubleExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a double property`() {
        // when
        val expression = queryPart {
            sumDistinct(Class1::double1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = Paths.path(Class1::double1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a double expression`() {
        // when
        val expression = queryPart {
            sumDistinct(doubleExpression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = doubleExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a bigInteger property`() {
        // when
        val expression = queryPart {
            sum(Class1::bigInteger1)
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = Paths.path(Class1::bigInteger1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a bigInteger expression`() {
        // when
        val expression = queryPart {
            sum(bigIntegerExpression1)
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = bigIntegerExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a bigInteger property`() {
        // when
        val expression = queryPart {
            sumDistinct(Class1::bigInteger1)
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = Paths.path(Class1::bigInteger1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a bigInteger expression`() {
        // when
        val expression = queryPart {
            sumDistinct(bigIntegerExpression1)
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = bigIntegerExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a bigDecimal property`() {
        // when
        val expression = queryPart {
            sum(Class1::bigDecimal1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = Paths.path(Class1::bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum() with a bigDecimal expression`() {
        // when
        val expression = queryPart {
            sum(bigDecimalExpression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            expr = bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a bigDecimal property`() {
        // when
        val expression = queryPart {
            sumDistinct(Class1::bigDecimal1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = Paths.path(Class1::bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct() with a bigDecimal expression`() {
        // when
        val expression = queryPart {
            sumDistinct(bigDecimalExpression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            expr = bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
