package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort

interface SelectQueryOrderByStep<T> : JpqlQueryable<SelectQuery<T>> {
    fun orderBy(vararg sorts: Sort): JpqlQueryable<SelectQuery<T>>

    fun orderBy(sorts: Collection<Sort>): JpqlQueryable<SelectQuery<T>>
}
