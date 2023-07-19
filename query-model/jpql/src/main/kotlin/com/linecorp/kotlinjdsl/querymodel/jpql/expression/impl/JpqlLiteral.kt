package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
sealed interface JpqlLiteral {
    data class ShortLiteral internal constructor(
        val short: Short
    ) : Expression<Short>

    data class IntLiteral internal constructor(
        val int: Int
    ) : Expression<Int>

    data class LongLiteral internal constructor(
        val long: Long
    ) : Expression<Long>

    data class FloatLiteral internal constructor(
        val float: Float
    ) : Expression<Float>

    data class DoubleLiteral internal constructor(
        val double: Double
    ) : Expression<Double>

    data class BooleanLiteral internal constructor(
        val boolean: Boolean
    ) : Expression<Boolean>

    data class StringLiteral internal constructor(
        val string: String
    ) : Expression<String>

    data class NullableShortLiteral internal constructor(
        val short: Short?
    ) : Expression<Short?>

    data class NullableIntLiteral internal constructor(
        val int: Int?
    ) : Expression<Int?>

    data class NullableLongLiteral internal constructor(
        val long: Long?
    ) : Expression<Long?>

    data class NullableFloatLiteral internal constructor(
        val float: Float?
    ) : Expression<Float?>

    data class NullableDoubleLiteral internal constructor(
        val double: Double?
    ) : Expression<Double?>

    data class NullableBooleanLiteral internal constructor(
        val boolean: Boolean?
    ) : Expression<Boolean?>

    data class NullableStringLiteral internal constructor(
        val string: String?
    ) : Expression<String?>
}
