package com.linecorp.kotlinjdsl.dsl.sql.select

import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable
import com.linecorp.kotlinjdsl.querymodel.sql.Table

interface SelectQueryFromStep : SqlQueryable<SelectQuery> {
    fun from(vararg tables: Table<*>): SelectQueryWhereStep
    fun from(tables: Iterable<Table<*>>): SelectQueryWhereStep
}
