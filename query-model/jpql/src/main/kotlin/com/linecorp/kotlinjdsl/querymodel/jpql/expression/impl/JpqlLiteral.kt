package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that represents the literal.
 *
 * **Numeric Literal**
 * - An int literal is represented by int value.
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
sealed interface JpqlLiteral<T : Any> : Expression<T> {
    data class IntLiteral internal constructor(
        val int: Int,
    ) : JpqlLiteral<Int>

    data class LongLiteral internal constructor(
        val long: Long,
    ) : JpqlLiteral<Long>

    data class FloatLiteral internal constructor(
        val float: Float,
    ) : JpqlLiteral<Float>

    data class DoubleLiteral internal constructor(
        val double: Double,
    ) : JpqlLiteral<Double>

    data class BooleanLiteral internal constructor(
        val boolean: Boolean,
    ) : JpqlLiteral<Boolean>

    data class CharLiteral internal constructor(
        val char: Char,
    ) : JpqlLiteral<Char>

    data class StringLiteral internal constructor(
        val string: String,
    ) : JpqlLiteral<String>

    data class EnumLiteral<T : Enum<T>> internal constructor(
        val enum: T,
    ) : JpqlLiteral<T>

    data object NullLiteral : JpqlLiteral<Any>
}
