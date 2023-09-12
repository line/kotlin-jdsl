package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

@SinceJdsl("3.0.0")
interface SelectQueryHavingStep<T : Any> : SelectQueryOrderByStep<T>, JpqlQueryable<SelectQuery<T>> {
    /**
     * Creates a having clause in a select query.
     */
    @SinceJdsl("3.0.0")
    fun having(predicate: Predicatable?): SelectQueryOrderByStep<T>

    /**
     * Creates a having clause in a select query.
     */
    @SinceJdsl("3.0.0")
    fun havingAnd(vararg predicates: Predicatable?): SelectQueryOrderByStep<T>

    /**
     * Creates a having clause in a select query.
     */
    @SinceJdsl("3.0.0")
    fun havingOr(vararg predicates: Predicatable?): SelectQueryOrderByStep<T>
}
