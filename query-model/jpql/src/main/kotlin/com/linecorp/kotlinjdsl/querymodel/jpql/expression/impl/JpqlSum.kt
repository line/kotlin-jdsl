package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import java.math.BigDecimal
import java.math.BigInteger

sealed interface JpqlSum<T : Number?> : Expression<T> {
    val expression: Expression<out Number?>
    val distinct: Boolean

    data class IntSum internal constructor(
        override val expression: Expression<Int?>,
        override val distinct: Boolean
    ) : JpqlSum<Long?>

    data class LongSum internal constructor(
        override val expression: Expression<Long?>,
        override val distinct: Boolean
    ) : JpqlSum<Long?>

    data class FloatSum internal constructor(
        override val expression: Expression<Float?>,
        override val distinct: Boolean
    ) : JpqlSum<Double?>

    data class DoubleSum internal constructor(
        override val expression: Expression<Double?>,
        override val distinct: Boolean
    ) : JpqlSum<Double?>

    data class BigIntegerSum internal constructor(
        override val expression: Expression<BigInteger?>,
        override val distinct: Boolean
    ) : JpqlSum<BigInteger?>

    data class BigDecimalSum internal constructor(
        override val expression: Expression<BigDecimal?>,
        override val distinct: Boolean
    ) : JpqlSum<BigDecimal?>
}
