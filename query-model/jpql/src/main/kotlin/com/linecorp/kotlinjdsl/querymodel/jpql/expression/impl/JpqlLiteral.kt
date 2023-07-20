package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that represents the literal.
 */
@Internal
sealed interface JpqlLiteral<T> : Expression<T> {
    data class ShortLiteral internal constructor(
        val short: Short
    ) : JpqlLiteral<Short>

    data class IntLiteral internal constructor(
        val int: Int
    ) : JpqlLiteral<Int>

    data class LongLiteral internal constructor(
        val long: Long
    ) : JpqlLiteral<Long>

    data class FloatLiteral internal constructor(
        val float: Float
    ) : JpqlLiteral<Float>

    data class DoubleLiteral internal constructor(
        val double: Double
    ) : JpqlLiteral<Double>

    data class BooleanLiteral internal constructor(
        val boolean: Boolean
    ) : JpqlLiteral<Boolean>

    data class StringLiteral internal constructor(
        val string: String
    ) : JpqlLiteral<String>

    data object NullLiteral : JpqlLiteral<Any?>
}
