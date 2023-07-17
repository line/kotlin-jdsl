package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlCaseValueWhen<T, V> internal constructor(
    val compareValue: Expression<T>,
    val result: Expression<V>,
)
