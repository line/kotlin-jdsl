package com.linecorp.kotlinjdsl.dsl.sql.select

import com.linecorp.kotlinjdsl.query.sql.SelectQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

interface SelectQueryLimitStep : SqlQueryable<SelectQuery> {
    fun offset(offset: Int): SqlQueryable<SelectQuery>
    fun limit(limit: Int): SqlQueryable<SelectQuery>
    fun limit(offset: Int, limit: Int): SqlQueryable<SelectQuery>
}
