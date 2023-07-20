@file:Suppress("RedundantNullableReturnType")

package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlAliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

class ExpressionsTest : WithAssertions {
    private val int1: Int = 1
    private val int2: Int = 2
    private val nullableInt1: Int? = 10
    private val nullableInt2: Int? = 20
    private val nullInt1: Int? = null

    private val long1: Long = 1
    private val float1: Float = 1.0f
    private val double1: Double = 1.0
    private val boolean1: Boolean = true
    private val string1: String = "string1"

    private val alias1: String = "alias1"
    private val alias2: String = "alias2"

    private val template1: String = "template1"

    private val paramName1: String = "paramName1"

    @Test
    fun value() {
        // when
        val expression = Expressions.value(int1).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlValue(int1))
    }

    @Test
    fun `nullable value`() {
        // when
        val expression = Expressions.value(nullableInt1).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlValue(nullableInt1))
    }

    @Test
    fun `null value`() {
        // when
        val expression = Expressions.value(nullInt1).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlNull)
    }

    @Test
    fun `literal int`() {
        // when
        val expression = Expressions.intLiteral(
            int1,
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlLiteral.IntLiteral(int1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `literal long`() {
        // when
        val expression = Expressions.longLiteral(
            long1,
        ).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlLiteral.LongLiteral(long1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `literal float`() {
        // when
        val expression = Expressions.floatLiteral(
            float1,
        ).toExpression()

        val actual: Expression<Float> = expression // for type check

        // then
        val expected = JpqlLiteral.FloatLiteral(float1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `literal double`() {
        // when
        val expression = Expressions.doubleLiteral(
            double1,
        ).toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = JpqlLiteral.DoubleLiteral(double1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `literal boolean`() {
        // when
        val expression = Expressions.booleanLiteral(
            boolean1,
        ).toExpression()

        val actual: Expression<Boolean> = expression // for type check

        // then
        val expected = JpqlLiteral.BooleanLiteral(boolean1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `literal string`() {
        // when
        val expression = Expressions.stringLiteral(
            string1,
        ).toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = JpqlLiteral.StringLiteral(string1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `param name`() {
        // when
        val expression = Expressions.param<Int>(paramName1).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlParam(paramName1, null))
    }

    @Test
    fun `nullable param name`() {
        // when
        val expression = Expressions.param<Int?>(paramName1).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlParam(paramName1, null))
    }

    @Test
    fun `param name value`() {
        // when
        val expression = Expressions.param(paramName1, int1).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlParam(paramName1, int1))
    }

    @Test
    fun `param name nullable value`() {
        // when
        val expression = Expressions.param(paramName1, nullableInt1).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlParam(paramName1, nullableInt1))
    }

    @Test
    fun `nullable param name value`() {
        // when
        val expression = Expressions.param<Int?>(paramName1, int1).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlParam(paramName1, int1))
    }

    @Test
    fun `nullable param name nullable value`() {
        // when
        val expression = Expressions.param(paramName1, nullableInt1).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        assertThat(actual).isEqualTo(JpqlParam(paramName1, nullableInt1))
    }

    @Test
    fun `count expression distinct false`() {
        // when
        val expression = Expressions.count(Paths.path(TestTable1::int1), distinct = false).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlCount(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count expression distinct true`() {
        // when
        val expression = Expressions.count(Paths.path(TestTable1::int1), distinct = true).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlCount(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count nullable expression distinct false`() {
        // when
        val expression = Expressions.count(Paths.path(TestTable1::nullableInt1), distinct = false).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlCount(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count nullable expression distinct true`() {
        // when
        val expression = Expressions.count(Paths.path(TestTable1::nullableInt1), distinct = true).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlCount(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max expression distinct false`() {
        // when
        val expression = Expressions.max(Paths.path(TestTable1::int1), distinct = false).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMax<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max expression distinct true`() {
        // when
        val expression = Expressions.max(Paths.path(TestTable1::int1), distinct = true).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMax<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max nullable expression distinct false`() {
        // when
        val expression = Expressions.max(Paths.path(TestTable1::nullableInt1), distinct = false).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMax<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max nullable expression distinct true`() {
        // when
        val expression = Expressions.max(Paths.path(TestTable1::nullableInt1), distinct = true).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMax<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min expression distinct false`() {
        // when
        val expression = Expressions.min(Paths.path(TestTable1::int1), distinct = false).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMin<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min expression distinct true`() {
        // when
        val expression = Expressions.min(Paths.path(TestTable1::int1), distinct = true).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMin<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min nullable expression distinct false`() {
        // when
        val expression = Expressions.min(Paths.path(TestTable1::nullableInt1), distinct = false).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMin<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min nullable expression distinct true`() {
        // when
        val expression = Expressions.min(Paths.path(TestTable1::nullableInt1), distinct = true).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlMin<Int>(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg expression distinct false`() {
        // when
        val expression = Expressions.avg(Paths.path(TestTable1::int1), distinct = false).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlAvg(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg expression distinct true`() {
        // when
        val expression = Expressions.avg(Paths.path(TestTable1::int1), distinct = true).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlAvg(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg nullable expression distinct false`() {
        // when
        val expression = Expressions.avg(Paths.path(TestTable1::nullableInt1), distinct = false).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlAvg(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg nullable expression distinct true`() {
        // when
        val expression = Expressions.avg(Paths.path(TestTable1::nullableInt1), distinct = true).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlAvg(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum int expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::int1), distinct = false).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.IntSum(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum int expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::int1), distinct = true).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.IntSum(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable int expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableInt1), distinct = false).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.IntSum(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable int expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableInt1), distinct = true).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.IntSum(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::long1), distinct = false).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.LongSum(
            JpqlField<Long>(
                Long::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::long1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum long expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::long1), distinct = true).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.LongSum(
            JpqlField<Long>(
                Long::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::long1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable long expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableLong1), distinct = false).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.LongSum(
            JpqlField<Long?>(
                Long::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableLong1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable long expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableLong1), distinct = true).toExpression()

        val actual: Expression<Long?> = expression // for type check

        // then
        val expected = JpqlSum.LongSum(
            JpqlField<Long?>(
                Long::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableLong1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::float1), distinct = false).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.FloatSum(
            JpqlField<Float>(
                Float::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::float1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum float expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::float1), distinct = true).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.FloatSum(
            JpqlField<Float>(
                Float::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::float1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable float expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableFloat1), distinct = false).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.FloatSum(
            JpqlField<Float?>(
                Float::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableFloat1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable float expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableFloat1), distinct = true).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.FloatSum(
            JpqlField<Float?>(
                Float::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableFloat1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::double1), distinct = false).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.DoubleSum(
            JpqlField<Double>(
                Double::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::double1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum double expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::double1), distinct = true).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.DoubleSum(
            JpqlField<Double>(
                Double::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::double1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable double expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableDouble1), distinct = false).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.DoubleSum(
            JpqlField<Double?>(
                Double::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableDouble1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable double expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableDouble1), distinct = true).toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = JpqlSum.DoubleSum(
            JpqlField<Double?>(
                Double::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableDouble1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::bigInteger1), distinct = false).toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = JpqlSum.BigIntegerSum(
            JpqlField<BigInteger>(
                BigInteger::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::bigInteger1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigInteger expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::bigInteger1), distinct = true).toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = JpqlSum.BigIntegerSum(
            JpqlField<BigInteger>(
                BigInteger::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::bigInteger1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigInteger expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableBigInteger1), distinct = false).toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = JpqlSum.BigIntegerSum(
            JpqlField<BigInteger?>(
                BigInteger::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableBigInteger1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigInteger expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableBigInteger1), distinct = true).toExpression()

        val actual: Expression<BigInteger?> = expression // for type check

        // then
        val expected = JpqlSum.BigIntegerSum(
            JpqlField<BigInteger?>(
                BigInteger::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableBigInteger1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::bigDecimal1), distinct = false).toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = JpqlSum.BigDecimalSum(
            JpqlField<BigDecimal>(
                BigDecimal::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::bigDecimal1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum bigDecimal expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::bigDecimal1), distinct = true).toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = JpqlSum.BigDecimalSum(
            JpqlField<BigDecimal>(
                BigDecimal::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::bigDecimal1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigDecimal expression distinct false`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableBigDecimal1), distinct = false).toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = JpqlSum.BigDecimalSum(
            JpqlField<BigDecimal?>(
                BigDecimal::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableBigDecimal1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sum nullable bigDecimal expression distinct true`() {
        // when
        val expression = Expressions.sum(Paths.path(TestTable1::nullableBigDecimal1), distinct = true).toExpression()

        val actual: Expression<BigDecimal?> = expression // for type check

        // then
        val expected = JpqlSum.BigDecimalSum(
            JpqlField<BigDecimal?>(
                BigDecimal::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableBigDecimal1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type expression expression`() {
        // when
        val expression = Expressions.new(
            Row::class,
            listOf(Paths.path(TestTable1::int1), Paths.path(TestTable1::int2)),
        ).toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = JpqlNew(
            Row::class,
            listOf<Path<Any?>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type expression nullable expression`() {
        // when
        val expression = Expressions.new(
            Row::class,
            listOf(Paths.path(TestTable1::int1), Paths.path(TestTable1::nullableInt1)),
        ).toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = JpqlNew(
            Row::class,
            listOf<Path<Any?>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type nullable expression nullable expression`() {
        // when
        val expression = Expressions.new(
            Row::class,
            listOf(Paths.path(TestTable1::nullableInt1), Paths.path(TestTable1::nullableInt2)),
        ).toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = JpqlNew(
            Row::class,
            listOf<Path<Any?>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt2.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case when more`() {
        // when
        val expression = Expressions.case(
            listOf(
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Expressions.value(int1),
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Expressions.value(nullableInt1),
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Paths.path(TestTable1::int1),
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Paths.path(TestTable1::nullableInt1),
            ),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCase<Int?>(
            whens = listOf(
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlValue(int1),
                ),
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlValue(nullableInt1),
                ),
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlField(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::int1.name,
                    ),
                ),
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlField(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else value`() {
        // when
        val expression = Expressions.case(
            listOf(
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Expressions.value(int1),
            ),
            Expressions.value(int1),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCase<Int>(
            whens = listOf(
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlValue(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else nullable value`() {
        // when
        val expression = Expressions.case(
            listOf(
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Expressions.value(int1),
            ),
            Expressions.value(nullableInt1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCase<Int?>(
            whens = listOf(
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlValue(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else expression`() {
        // when
        val expression = Expressions.case(
            listOf(
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Expressions.value(int1),
            ),
            Paths.path(TestTable1::int1),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCase<Int>(
            whens = listOf(
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else nullable expression`() {
        // when
        val expression = Expressions.case(
            listOf(
                Predicates.equal(
                    Paths.path(TestTable1::string1),
                    Expressions.value(string1),
                ) to Expressions.value(int1),
            ),
            Paths.path(TestTable1::nullableInt1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCase<Int?>(
            whens = listOf(
                JpqlCaseWhen(
                    predicate = JpqlEqual(
                        value = JpqlField<String>(
                            String::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::string1.name,
                        ),
                        compareValue = JpqlValue(string1),
                    ),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue expression when more`() {
        // when
        val expression = Expressions.caseValue(
            Paths.path(TestTable1::string1),
            listOf(
                Expressions.value(string1) to Expressions.value(int1),
                Expressions.value(string1) to Expressions.value(nullableInt1),
                Expressions.value(string1) to Paths.path(TestTable1::int1),
                Expressions.value(string1) to Paths.path(TestTable1::nullableInt1),

                Paths.path(TestTable1::string1) to Expressions.value(int1),
                Paths.path(TestTable1::string1) to Expressions.value(nullableInt1),
                Paths.path(TestTable1::string1) to Paths.path(TestTable1::int1),
                Paths.path(TestTable1::string1) to Paths.path(TestTable1::nullableInt1),
            ),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCaseValue<Int?>(
            value = JpqlField<String>(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            whens = listOf(
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlValue(int1),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlValue(nullableInt1),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlField(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::int1.name,
                    ),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlField(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::nullableInt1.name,
                    ),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlField(
                        String::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::string1.name,
                    ),
                    result = JpqlValue(int1),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlField(
                        String::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::string1.name,
                    ),
                    result = JpqlValue(nullableInt1),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlField(
                        String::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::string1.name,
                    ),
                    result = JpqlField(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::int1.name,
                    ),
                ),
                JpqlCaseValueWhen(
                    compareValue = JpqlField(
                        String::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::string1.name,
                    ),
                    result = JpqlField(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when value then value else value`() {
        // when
        val expression = Expressions.caseValue(
            Paths.path(TestTable1::string1),
            listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            Expressions.value(int1),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCaseValue<Int>(
            value = JpqlField<String>(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            whens = listOf(
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlValue(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when value then value else nullable value`() {
        // when
        val expression = Expressions.caseValue(
            Paths.path(TestTable1::string1),
            listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            Expressions.value(nullableInt1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCaseValue<Int?>(
            value = JpqlField<String>(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            whens = listOf(
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlValue(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue expression when value then value else expression`() {
        // when
        val expression = Expressions.caseValue(
            Paths.path(TestTable1::string1),
            listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            Paths.path(TestTable1::int1),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCaseValue<Int>(
            value = JpqlField<String>(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            whens = listOf(
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue expression when value then value else nullable expression`() {
        // when
        val expression = Expressions.caseValue(
            Paths.path(TestTable1::string1),
            listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            Paths.path(TestTable1::nullableInt1),
        )

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCaseValue<Int?>(
            value = JpqlField<String>(
                String::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::string1.name,
            ),
            whens = listOf(
                JpqlCaseValueWhen(
                    compareValue = JpqlValue(string1),
                    result = JpqlValue(int1),
                ),
            ),
            `else` = JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
            emptyList(),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlValue(int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression nullable expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::int1),
            Expressions.value(nullableInt1),
            emptyList(),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlValue(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(nullableInt1),
            emptyList(),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression with non-null type`() {
        // when
        val expression = Expressions.coalesce<Int>(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(nullableInt1),
            emptyList(),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression expression expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
            listOf(
                Expressions.value(int2),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlValue(int1),
                JpqlValue(int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression expression nullable expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
            listOf(
                Expressions.value(nullableInt1),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlValue(int1),
                JpqlValue(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce expression nullable expression nullable expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::int1),
            Expressions.value(nullableInt1),
            listOf(
                Expressions.value(nullableInt2),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlValue(nullableInt1),
                JpqlValue(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression expression expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(int1),
            listOf(
                Expressions.value(int2),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(int1),
                JpqlValue(int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression expression nullable expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(int1),
            listOf(
                Expressions.value(nullableInt1),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(int1),
                JpqlValue(nullableInt1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression nullable expression`() {
        // when
        val expression = Expressions.coalesce(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(nullableInt1),
            listOf(
                Expressions.value(nullableInt2),
            ),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(nullableInt1),
                JpqlValue(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `coalesce nullable expression nullable expression nullable expression with non-null type`() {
        // when
        val expression = Expressions.coalesce<Int>(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(nullableInt1),
            listOf(
                Expressions.value(nullableInt2),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCoalesce(
            listOf(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(nullableInt1),
                JpqlValue(nullableInt2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullIf expression expression`() {
        // when
        val expression = Expressions.nullIf(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlNullIf(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValue = JpqlValue(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf expression nullable expression`() {
        // when
        val expression = Expressions.nullIf(
            Paths.path(TestTable1::int1),
            Expressions.value(nullableInt1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlNullIf(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            compareValue = JpqlValue(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf nullable expression expression`() {
        // when
        val expression = Expressions.nullIf(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(int1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlNullIf(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValue = JpqlValue(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `nullIf nullable expression nullable expression`() {
        // when
        val expression = Expressions.nullIf(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(nullableInt1),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlNullIf(
            value = JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            compareValue = JpqlValue(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `type path`() {
        // when
        val expression = Expressions.type(Paths.path(TestTable1::table1)).toExpression()

        val actual: Expression<KClass<SuperTable1>> = expression // for type check

        // then
        val expected = JpqlType<SuperTable1, KClass<SuperTable1>>(
            JpqlField<SuperTable1>(
                SuperTable1::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::table1.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `type nullable path`() {
        // when
        val expression = Expressions.type(Paths.path(TestTable1::nullableTable1)).toExpression()

        val actual: Expression<KClass<SuperTable1>?> = expression // for type check

        // then
        val expected = JpqlType<SuperTable1, KClass<SuperTable1>?>(
            JpqlField<SuperTable1>(
                SuperTable1::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableTable1.name,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template`() {
        // when
        val expression = Expressions.customExpression<Int>(
            template1,
            emptyList(),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCustomExpression<Int>(
            template1,
            emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template expression`() {
        // when
        val expression = Expressions.customExpression<Int>(
            template1,
            listOf(
                Paths.path(TestTable1::int1),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCustomExpression<Int>(
            template1,
            listOf(
                JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template nullable expression`() {
        // when
        val expression = Expressions.customExpression<Int>(
            template1,
            listOf(
                Paths.path(TestTable1::nullableInt1),
            ),
        ).toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = JpqlCustomExpression<Int>(
            template1,
            listOf(
                JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression nullable type template`() {
        // when
        val expression = Expressions.customExpression<Int?>(
            template1,
            emptyList(),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCustomExpression<Int?>(
            template1,
            emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression nullable type template expression`() {
        // when
        val expression = Expressions.customExpression<Int?>(
            template1,
            listOf(
                Paths.path(TestTable1::int1),
            ),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCustomExpression<Int>(
            template1,
            listOf(
                JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression nullable type template nullable expression`() {
        // when
        val expression = Expressions.customExpression<Int?>(
            template1,
            listOf(
                Paths.path(TestTable1::nullableInt1),
            ),
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlCustomExpression<Int?>(
            template1,
            listOf(
                JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias expression string`() {
        // when
        val expression = Expressions.alias(
            Expressions.count(Paths.path(TestTable1::int1), distinct = false),
            alias1,
        ).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlAliasedExpression(
            JpqlCount(
                JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias nullable expression string`() {
        // when
        val expression = Expressions.alias(
            Expressions.max(Paths.path<Int?>(TestTable1::nullableInt1), distinct = false),
            alias1,
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlAliasedExpression<Int?>(
            JpqlMax(
                JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias alias expression string string`() {
        // when
        val expression = Expressions.alias(
            Expressions.alias(
                Expressions.count(Paths.path(TestTable1::int1), distinct = false),
                alias1,
            ),
            alias2,
        ).toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = JpqlAliasedExpression(
            JpqlCount(
                JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                distinct = false,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias alias nullable expression string string`() {
        // when
        val expression = Expressions.alias(
            Expressions.alias(
                Expressions.max(Paths.path<Int?>(TestTable1::nullableInt1), distinct = false),
                alias1,
            ),
            alias2,
        ).toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = JpqlAliasedExpression<Int?>(
            JpqlMax(
                JpqlField<Int?>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                distinct = false,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `pair expression expression`() {
        // when
        val expression = Expressions.pair(
            Expressions.count(Paths.path(TestTable1::int1), distinct = false),
            Expressions.value(long1),
        )

        val actual: ExpressionAndExpression<Long> = expression

        // then
        assertThat(actual).isEqualTo(
            JpqlExpressionAndExpression(
                JpqlCount(
                    JpqlField<Int>(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::int1.name,
                    ),
                    false,
                ),
                JpqlValue(long1),
            ),
        )
    }

    @Test
    fun `pair nullable expression expression`() {
        // when
        val expression = Expressions.pair(
            Expressions.max(Paths.path(TestTable1::nullableInt1), distinct = false),
            Expressions.value(int1),
        )

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            JpqlExpressionAndExpression(
                JpqlMax(
                    JpqlField<Int?>(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::nullableInt1.name,
                    ),
                    false,
                ),
                JpqlValue(int1),
            ),
        )
    }

    @Test
    fun `pair nullable expression nullable expression`() {
        // when
        val expression = Expressions.pair(
            Expressions.max(Paths.path(TestTable1::nullableInt1), distinct = false),
            Expressions.value(nullableInt1),
        )

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            JpqlExpressionAndExpression(
                JpqlMax(
                    JpqlField<Int?>(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::nullableInt1.name,
                    ),
                    false,
                ),
                JpqlValue(nullableInt1),
            ),
        )
    }

    private class TestTable1 {
        val string1: String = "string1"

        val int1: Int = 1
        val int2: Int = 1
        val nullableInt1: Int? = null
        val nullableInt2: Int? = null

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

        val table1: SuperTable1 = SuperTable1()
        val nullableTable1: SuperTable1? = null
    }

    private open class SuperTable1

    private class Row
}
