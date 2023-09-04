package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable

@SinceJdsl("3.0.0")
interface AssociationJoinAsStep<T : Any> : Joinable {
    /**
     * Creates an aliased join.
     */
    @SinceJdsl("3.0.0")
    fun `as`(entity: Entityable<T>): Joinable

    /**
     * Creates an aliased join.
     */
    @SinceJdsl("3.0.0")
    fun alias(entity: Entityable<T>): Joinable
}
