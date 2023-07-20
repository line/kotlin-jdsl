package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that represents the literal.
 *
 * **Numeric Literal**
 * - A short literal is represented by short value.
 * - An int literal by int value.
 * - A long literal by long value with the L suffix.
 * - A float literal by float value with the F suffix.
 * - A double literal by double value.
 *
 * **Boolean Literal**
 * - A boolean literal is represented by `TRUE` or `FALSE`.
 *
 * **String Literal**
 * - A string literal is enclosed in single quotes—for example: ‘literal’.
 * - A string literal that includes a single quote is represented by two single quotes—for example: `‘literal’’s’`.
 *
 * **Null Literal**
 * - A null literal is represented by `NULL`.
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
