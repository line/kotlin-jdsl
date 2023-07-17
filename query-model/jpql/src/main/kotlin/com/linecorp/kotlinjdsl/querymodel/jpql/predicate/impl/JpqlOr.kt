package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

data class JpqlOr internal constructor(
    val predicates: Collection<Predicate?>,
) : Predicate
