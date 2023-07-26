package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

interface SelectQueryHavingStep<T : Any> : SelectQueryOrderByStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun having(predicate: Predicatable?): SelectQueryOrderByStep<T>

    fun havingAnd(vararg predicates: Predicatable?): SelectQueryOrderByStep<T>

    fun havingAnd(predicates: Iterable<Predicatable?>): SelectQueryOrderByStep<T>

    fun havingOr(vararg predicates: Predicatable?): SelectQueryOrderByStep<T>

    fun havingOr(predicates: Iterable<Predicatable?>): SelectQueryOrderByStep<T>
}
