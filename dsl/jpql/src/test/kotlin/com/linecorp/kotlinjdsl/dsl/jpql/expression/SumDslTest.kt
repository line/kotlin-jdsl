package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class SumDslTest : AbstractJpqlDslTest() {
    @Test
    fun `sum int property`() {
        // when
        val expression = testJpql {
            sum(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct int property`() {
        // when
        val expression = testJpql {
            sumDistinct(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long property`() {
        // when
        val expression = testJpql {
            sum(TestTable1::long1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::long1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct long property`() {
        // when
        val expression = testJpql {
            sumDistinct(TestTable1::long1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::long1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float property`() {
        // when
        val expression = testJpql {
            sum(TestTable1::float1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::float1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct float property`() {
        // when
        val expression = testJpql {
            sumDistinct(TestTable1::float1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::float1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double property`() {
        // when
        val expression = testJpql {
            sum(TestTable1::double1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::double1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct double property`() {
        // when
        val expression = testJpql {
            sumDistinct(TestTable1::double1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::double1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger property`() {
        // when
        val expression = testJpql {
            sum(TestTable1::bigInteger1)
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::bigInteger1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct bigInteger property`() {
        // when
        val expression = testJpql {
            sumDistinct(TestTable1::bigInteger1)
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::bigInteger1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal property`() {
        // when
        val expression = testJpql {
            sum(TestTable1::bigDecimal1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct bigDecimal property`() {
        // when
        val expression = testJpql {
            sumDistinct(TestTable1::bigDecimal1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum int expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct int expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable1::long1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::long1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct long expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable1::long1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::long1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable1::float1))
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::float1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct float expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable1::float1))
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::float1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable1::double1))
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::double1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct double expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable1::double1))
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::double1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable1::bigInteger1))
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::bigInteger1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct bigInteger expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable1::bigInteger1))
        }.toExpression()

        val actual: Expression<BigInteger> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::bigInteger1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable1::bigDecimal1))
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = false,
            Paths.path(TestTable1::bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct bigDecimal expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable1::bigDecimal1))
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.sum(
            distinct = true,
            Paths.path(TestTable1::bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
        val long1: Long = 1
        val float1: Float = 1f
        val double1: Double = 1.0
        val bigInteger1: BigInteger = BigInteger.ONE
        val bigDecimal1: BigDecimal = BigDecimal.ONE
    }
}
