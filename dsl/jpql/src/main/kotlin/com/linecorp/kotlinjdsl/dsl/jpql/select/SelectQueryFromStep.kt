package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

interface SelectQueryFromStep<T : Any> {
    fun from(vararg froms: Fromable?): SelectQueryWhereStep<T>

    fun from(froms: Iterable<Fromable?>): SelectQueryWhereStep<T>
}
