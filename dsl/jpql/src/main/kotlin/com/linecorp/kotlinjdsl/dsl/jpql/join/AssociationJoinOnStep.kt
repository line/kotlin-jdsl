package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

@SinceJdsl("3.0.0")
interface AssociationJoinOnStep<T : Any> : AssociationJoinAsStep<T>, Joinable {
    /**
     * Creates an on operator in a join clause.
     */
    @SinceJdsl("3.0.0")
    fun on(predicate: Predicatable): AssociationJoinAsStep<T>
}
