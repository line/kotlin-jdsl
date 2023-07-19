package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import java.math.BigDecimal
import java.math.BigInteger

@Internal
sealed interface JpqlSum<T : Number?> : Expression<T> {
    val expr: Expression<out Number?>
    val distinct: Boolean

    data class IntSum internal constructor(
        override val expr: Expression<Int?>,
        override val distinct: Boolean
    ) : JpqlSum<Long?>

    data class LongSum internal constructor(
        override val expr: Expression<Long?>,
        override val distinct: Boolean
    ) : JpqlSum<Long?>

    data class FloatSum internal constructor(
        override val expr: Expression<Float?>,
        override val distinct: Boolean
    ) : JpqlSum<Double?>

    data class DoubleSum internal constructor(
        override val expr: Expression<Double?>,
        override val distinct: Boolean
    ) : JpqlSum<Double?>

    data class BigIntegerSum internal constructor(
        override val expr: Expression<BigInteger?>,
        override val distinct: Boolean
    ) : JpqlSum<BigInteger?>

    data class BigDecimalSum internal constructor(
        override val expr: Expression<BigDecimal?>,
        override val distinct: Boolean
    ) : JpqlSum<BigDecimal?>
}
