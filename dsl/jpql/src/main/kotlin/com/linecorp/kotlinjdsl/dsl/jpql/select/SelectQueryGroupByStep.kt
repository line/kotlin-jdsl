package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

@SinceJdsl("3.0.0")
interface SelectQueryGroupByStep<T : Any> : SelectQueryHavingStep<T>, JpqlQueryable<SelectQuery<T>> {
    /**
     * Creates a group by clause in a select query.
     */
    @SinceJdsl("3.0.0")
    fun groupBy(vararg expr: Expressionable<*>?): SelectQueryHavingStep<T>
}
