package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

interface AssociationJoinOnStep<T : Any> : AssociationJoinAsStep<T>, Joinable {
    fun on(predicate: Predicate): Joinable
}
