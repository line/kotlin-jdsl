package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable

interface JoinAsStep<T : Any> : Joinable {
    fun `as`(entity: Entity<T>): Joinable

    fun alias(entity: Entity<T>): Joinable
}
