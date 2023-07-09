package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

@Internal
data class CaseWhen<T>(
    val predicate: Predicate,
    val then: Expression<T>,
)
