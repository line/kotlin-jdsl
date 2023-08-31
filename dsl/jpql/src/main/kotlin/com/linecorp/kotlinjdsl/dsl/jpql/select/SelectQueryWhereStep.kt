package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

@SinceJdsl("3.0.0")
interface SelectQueryWhereStep<T : Any> : SelectQueryGroupByStep<T>, JpqlQueryable<SelectQuery<T>> {
    @SinceJdsl("3.0.0")
    fun where(predicate: Predicatable?): SelectQueryGroupByStep<T>

    @SinceJdsl("3.0.0")
    fun whereAnd(vararg predicates: Predicatable?): SelectQueryGroupByStep<T>

    @SinceJdsl("3.0.0")
    fun whereOr(vararg predicates: Predicatable?): SelectQueryGroupByStep<T>
}
