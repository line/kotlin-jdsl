package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlIndex<T : Any>internal constructor(
    val path: Expression<T>,
) : Expression<Int>
