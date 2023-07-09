package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.Sort

interface SelectQueryOrderByStep : JpqlQueryable<SelectQuery> {
    fun orderBy(vararg sorts: Sort): JpqlQueryable<SelectQuery>

    fun orderBy(sorts: Collection<Sort>): JpqlQueryable<SelectQuery>
}
