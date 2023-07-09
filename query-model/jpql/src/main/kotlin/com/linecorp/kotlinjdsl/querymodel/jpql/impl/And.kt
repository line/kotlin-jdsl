package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

data class And(
    val predicates: Collection<Predicate>,
) : Predicate
