package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlCaseValue<T> internal constructor(
    val value: Expression<*>,
    val whens: Iterable<JpqlCaseValueWhen<*, *>>,
    val `else`: Expression<*>?,
) : Expression<T>
