package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryWhereStep<T> : SelectQueryGroupByStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun where(predicate: Predicatable): SelectQueryGroupByStep<T>

    fun whereAnd(vararg predicates: Predicatable): SelectQueryGroupByStep<T>

    fun whereAnd(predicates: Collection<Predicatable>): SelectQueryGroupByStep<T>

    fun whereOr(vararg predicates: Predicatable): SelectQueryGroupByStep<T>

    fun whereOr(predicates: Collection<Predicatable>): SelectQueryGroupByStep<T>
}
