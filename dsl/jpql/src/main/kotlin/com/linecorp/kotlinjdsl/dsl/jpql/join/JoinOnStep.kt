package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

interface JoinOnStep<T : Any> {
    fun on(predicate: Predicate): JoinAsStep<T>
}
