package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import java.math.BigDecimal
import java.math.BigInteger

@Internal
sealed interface JpqlSum<T : Any, R : Any> : Expression<R> {
    val expr: Expression<T>
    val distinct: Boolean

    data class IntSum internal constructor(
        override val distinct: Boolean,
        override val expr: Expression<Int>,
    ) : JpqlSum<Int, Long>

    data class LongSum internal constructor(
        override val distinct: Boolean,
        override val expr: Expression<Long>,
    ) : JpqlSum<Long, Long>

    data class FloatSum internal constructor(
        override val distinct: Boolean,
        override val expr: Expression<Float>,
    ) : JpqlSum<Float, Double>

    data class DoubleSum internal constructor(
        override val distinct: Boolean,
        override val expr: Expression<Double>,
    ) : JpqlSum<Double, Double>

    data class BigIntegerSum internal constructor(
        override val distinct: Boolean,
        override val expr: Expression<BigInteger>,
    ) : JpqlSum<BigInteger, BigInteger>

    data class BigDecimalSum internal constructor(
        override val distinct: Boolean,
        override val expr: Expression<BigDecimal>,
    ) : JpqlSum<BigDecimal, BigDecimal>
}
