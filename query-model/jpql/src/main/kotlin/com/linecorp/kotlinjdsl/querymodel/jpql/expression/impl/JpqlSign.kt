package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that calculates the sign of a numeric [value].
 */
@Internal
data class JpqlSign<T : Number> internal constructor(
    val value: Expression<T>,
) : Expression<Int>
