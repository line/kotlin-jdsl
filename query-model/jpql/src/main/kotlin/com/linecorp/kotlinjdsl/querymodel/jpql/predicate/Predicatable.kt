package com.linecorp.kotlinjdsl.querymodel.jpql.predicate

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface Predicatable {
    @SinceJdsl("3.0.0")
    fun toPredicate(): Predicate
}
