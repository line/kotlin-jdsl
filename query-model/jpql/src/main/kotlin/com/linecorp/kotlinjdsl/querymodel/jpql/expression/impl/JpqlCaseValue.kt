package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlCaseValue<T, V> internal constructor(
    val value: Expression<T>,
    val whens: Collection<JpqlCaseValueWhen<T, V>>,
    val `else`: Expression<out V>?,
) : Expression<V>
