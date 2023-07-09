package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

@Internal
data class CaseValueWhen<T, V>(
    val compareValue: Expression<T>,
    val then: Expression<V>,
)
