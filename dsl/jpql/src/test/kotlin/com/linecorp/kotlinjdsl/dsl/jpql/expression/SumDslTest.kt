package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class SumDslTest : AbstractJpqlDslTest() {
    @Test
    fun `sum int expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum int expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::int1), distinct = true)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum int expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::int1), distinct = false)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::int1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable int expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable int expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableInt1), distinct = true)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableInt1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable int expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableInt1), distinct = false)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableInt1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct int expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::int1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct nullable int expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableInt1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::long1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::long1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::long1), distinct = true)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::long1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::long1), distinct = false)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::long1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable long expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableLong1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableLong1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable long expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableLong1), distinct = true)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableLong1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable long expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableLong1), distinct = false)
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableLong1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct long expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::long1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::long1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct nullable long expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::nullableLong1))
        }.toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableLong1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::float1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::float1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::float1), distinct = true)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::float1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::float1), distinct = false)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::float1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable float expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableFloat1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableFloat1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable float expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableFloat1), distinct = true)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableFloat1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable float expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableFloat1), distinct = false)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableFloat1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct float expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::float1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::float1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct nullable float expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::nullableFloat1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableFloat1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::double1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::double1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::double1), distinct = true)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::double1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::double1), distinct = false)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::double1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable double expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableDouble1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableDouble1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable double expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableDouble1), distinct = true)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableDouble1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable double expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableDouble1), distinct = false)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableDouble1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct double expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::double1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::double1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct nullable double expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::nullableDouble1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableDouble1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::bigInteger1))
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigInteger1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::bigInteger1), distinct = true)
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigInteger1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::bigInteger1), distinct = false)
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigInteger1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigInteger expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableBigInteger1))
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigInteger1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigInteger expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableBigInteger1), distinct = true)
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigInteger1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigInteger expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableBigInteger1), distinct = false)
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigInteger1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct bigInteger expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::bigInteger1))
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigInteger1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct nullable bigInteger expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::nullableBigInteger1))
        }.toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigInteger1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::bigDecimal1))
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigDecimal1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::bigDecimal1), distinct = true)
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigDecimal1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::bigDecimal1), distinct = false)
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigDecimal1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigDecimal expression`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableBigDecimal1))
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigDecimal1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigDecimal expression distinct true`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableBigDecimal1), distinct = true)
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigDecimal1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigDecimal expression distinct false`() {
        // when
        val expression = testJpql {
            sum(path(TestTable::nullableBigDecimal1), distinct = false)
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigDecimal1),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct bigDecimal expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::bigDecimal1))
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::bigDecimal1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sumDistinct nullable bigDecimal expression`() {
        // when
        val expression = testJpql {
            sumDistinct(path(TestTable::nullableBigDecimal1))
        }.toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = Expressions.sum(
            Paths.path(TestTable::nullableBigDecimal1),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val nullableInt1: Int? = null

        val long1: Long = 1
        val nullableLong1: Long? = null

        val float1: Float = 1f
        val nullableFloat1: Float? = null

        val double1: Double = 1.0
        val nullableDouble1: Double? = null

        val bigInteger1: BigInteger = BigInteger.ONE
        val nullableBigInteger1: BigInteger? = null

        val bigDecimal1: BigDecimal = BigDecimal.ONE
        val nullableBigDecimal1: BigDecimal? = null
    }
}
