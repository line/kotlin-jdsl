package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

@Internal
data class CaseValue<T, V>(
    val value: Expression<T>,
    val whens: Collection<CaseValueWhen<T, V>>,
    val `else`: Expression<out V>?,
) : Expression<V>
