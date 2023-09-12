package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlAnd internal constructor(
    val predicates: Iterable<Predicate>,
) : Predicate
