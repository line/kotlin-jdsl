package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

interface SelectQueryFromStep<T : Any> {
    fun from(entity: Entity<*>): SelectQueryWhereStep<T>

    fun from(entity: Entity<*>, vararg froms: Fromable?): SelectQueryWhereStep<T>
}
