package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

interface SelectQueryOrderByStep<T : Any> : JpqlQueryable<SelectQuery<T>> {
    fun orderBy(vararg sorts: Sortable?): JpqlQueryable<SelectQuery<T>>
}
