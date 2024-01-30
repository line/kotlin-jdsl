package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that calculates the ceiling of [value].
 */
@Internal
data class JpqlCeiling<T : Number> internal constructor(
    val value: Expression<T>,
) : Expression<T>
