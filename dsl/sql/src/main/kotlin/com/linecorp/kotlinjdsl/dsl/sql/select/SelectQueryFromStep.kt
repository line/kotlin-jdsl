package com.linecorp.kotlinjdsl.dsl.sql.select

import com.linecorp.kotlinjdsl.query.sql.SelectQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable
import com.linecorp.kotlinjdsl.query.sql.Table

interface SelectQueryFromStep : SqlQueryable<SelectQuery> {
    fun from(vararg tables: Table<*>): SelectQueryWhereStep
    fun from(tables: Collection<Table<*>>): SelectQueryWhereStep
}
