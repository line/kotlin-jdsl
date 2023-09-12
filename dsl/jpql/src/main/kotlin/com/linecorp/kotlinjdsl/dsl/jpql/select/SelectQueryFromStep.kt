package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

@SinceJdsl("3.0.0")
interface SelectQueryFromStep<T : Any> {
    /**
     * Creates a from clause in a select query.
     */
    @SinceJdsl("3.0.0")
    fun from(entity: Entityable<*>): SelectQueryWhereStep<T>

    /**
     * Creates a from clause in a select query.
     */
    @SinceJdsl("3.0.0")
    fun from(entity: Entityable<*>, vararg froms: Fromable?): SelectQueryWhereStep<T>
}
