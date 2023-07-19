package com.linecorp.kotlinjdsl.dsl.sql.select

import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.Sort
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

interface SelectQueryOrderByStep : SelectQueryLimitStep, SqlQueryable<SelectQuery> {
    fun orderBy(vararg sorts: Sort): SelectQueryLimitStep
    fun orderBy(sorts: Iterable<Sort>): SelectQueryLimitStep
}
