package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that adds [value1] by [value2].
 */
@Internal
data class JpqlPlus<T : Number, S : T?> internal constructor(
    val value1: Expression<in S>,
    val value2: Expression<in S>,
) : Expression<T>
