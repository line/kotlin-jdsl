package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

@SinceJdsl("3.0.0")
interface SelectQueryOrderByStep<T : Any> : JpqlQueryable<SelectQuery<T>> {
    @SinceJdsl("3.0.0")
    fun orderBy(vararg sorts: Sortable?): JpqlQueryable<SelectQuery<T>>
}
