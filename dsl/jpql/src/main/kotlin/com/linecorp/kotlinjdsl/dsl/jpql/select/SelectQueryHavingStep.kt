package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryHavingStep<T> : SelectQueryOrderByStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun having(predicate: Predicatable): SelectQueryOrderByStep<T>

    fun havingAnd(vararg predicates: Predicatable): SelectQueryOrderByStep<T>

    fun havingAnd(predicates: Collection<Predicatable>): SelectQueryOrderByStep<T>

    fun havingOr(vararg predicates: Predicatable): SelectQueryOrderByStep<T>

    fun havingOr(predicates: Collection<Predicatable>): SelectQueryOrderByStep<T>
}
