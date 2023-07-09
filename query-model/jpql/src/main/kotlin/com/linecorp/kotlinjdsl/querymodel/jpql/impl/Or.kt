package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

data class Or(
    val predicates: Collection<Predicate>,
) : Predicate
