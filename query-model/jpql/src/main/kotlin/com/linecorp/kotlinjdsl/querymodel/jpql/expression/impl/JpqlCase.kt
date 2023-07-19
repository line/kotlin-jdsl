package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlCase<T> internal constructor(
    val whens: Iterable<JpqlCaseWhen<*>>,
    val `else`: Expression<*>?,
) : Expression<T>
