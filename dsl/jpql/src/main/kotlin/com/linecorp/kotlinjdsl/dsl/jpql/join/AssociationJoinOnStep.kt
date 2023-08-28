package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

interface AssociationJoinOnStep<T : Any> : AssociationJoinAsStep<T>, Joinable {
    fun on(predicate: Predicatable): Joinable
}
