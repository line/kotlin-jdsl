package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

interface SelectQueryFromStep<T : Any> {
    fun from(entity: Entityable<*>): SelectQueryWhereStep<T>

    fun from(entity: Entityable<*>, vararg froms: Fromable?): SelectQueryWhereStep<T>
}
