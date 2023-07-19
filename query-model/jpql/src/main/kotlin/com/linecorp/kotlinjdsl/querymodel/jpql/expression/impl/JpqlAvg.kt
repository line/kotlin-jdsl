package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlAvg<T : Number, S : T?> internal constructor(
    val expr: Expression<in S>,
    val distinct: Boolean,
) : Expression<Double?>
