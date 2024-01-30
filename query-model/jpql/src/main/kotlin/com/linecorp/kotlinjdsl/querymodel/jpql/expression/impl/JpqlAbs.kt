package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that applies the absolute value function to [value].
 */
@Internal
data class JpqlAbs<T : Number> internal constructor(
    val value: Expression<*>,
) : Expression<T>
