package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

interface SelectQueryWhereStep<T : Any> : SelectQueryGroupByStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun where(predicate: Predicatable?): SelectQueryGroupByStep<T>

    fun whereAnd(vararg predicates: Predicatable?): SelectQueryGroupByStep<T>

    fun whereOr(vararg predicates: Predicatable?): SelectQueryGroupByStep<T>
}
