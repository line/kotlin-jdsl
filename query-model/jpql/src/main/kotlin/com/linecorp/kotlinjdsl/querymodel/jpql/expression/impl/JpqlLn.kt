package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that applies natural logarithm function to [value].
 */
@Internal
data class JpqlLn<T : Number> internal constructor(
    val value: Expression<T>,
) : Expression<Double>
