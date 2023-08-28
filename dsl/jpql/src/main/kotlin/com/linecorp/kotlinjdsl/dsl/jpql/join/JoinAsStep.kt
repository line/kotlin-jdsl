package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable

interface JoinAsStep<T : Any> : Joinable {
    fun `as`(entity: Entityable<T>): Joinable

    fun alias(entity: Entityable<T>): Joinable
}
