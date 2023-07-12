package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import java.math.BigDecimal
import java.math.BigInteger

sealed interface Sum<T : Number?> : Expression<T> {
    val expression: Expression<out Number?>
    val distinct: Boolean

    data class IntSum(
        override val expression: Expression<Int?>,
        override val distinct: Boolean
    ) : Sum<Long?>

    data class LongSum(
        override val expression: Expression<Long?>,
        override val distinct: Boolean
    ) : Sum<Long?>

    data class FloatSum(
        override val expression: Expression<Float?>,
        override val distinct: Boolean
    ) : Sum<Double?>

    data class DoubleSum(
        override val expression: Expression<Double?>,
        override val distinct: Boolean
    ) : Sum<Double?>

    data class BigIntegerSum(
        override val expression: Expression<BigInteger?>,
        override val distinct: Boolean
    ) : Sum<BigInteger?>

    data class BigDecimalSum(
        override val expression: Expression<BigDecimal?>,
        override val distinct: Boolean
    ) : Sum<BigDecimal?>
}
