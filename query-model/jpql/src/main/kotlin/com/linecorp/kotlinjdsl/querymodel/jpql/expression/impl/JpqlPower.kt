package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that calculates the rounding of a numeric [value] to a specified [scale].
 */
@Internal
data class JpqlPower<T : Number> internal constructor(
    val value: Expression<T>,
    val scale: Expression<Int>,
) : Expression<T>
