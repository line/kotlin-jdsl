package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that applies exponential function to [value].
 */
@Internal
data class JpqlExp<T : Number> internal constructor(
    val value: Expression<T>,
) : Expression<Double>
