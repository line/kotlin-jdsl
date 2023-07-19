package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlConcat<T : String?> internal constructor(
    val expr: Iterable<Expression<out T>>
) : Expression<T>
