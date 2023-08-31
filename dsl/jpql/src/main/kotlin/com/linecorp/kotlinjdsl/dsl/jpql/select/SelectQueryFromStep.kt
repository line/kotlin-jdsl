package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

@SinceJdsl("3.0.0")
interface SelectQueryFromStep<T : Any> {
    @SinceJdsl("3.0.0")
    fun from(entity: Entityable<*>): SelectQueryWhereStep<T>

    @SinceJdsl("3.0.0")
    fun from(entity: Entityable<*>, vararg froms: Fromable?): SelectQueryWhereStep<T>
}
