package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that calculates the square root of [value].
 */
@Internal
data class JpqlSqrt<T : Number> internal constructor(
    val value: Expression<T>,
) : Expression<Double>
