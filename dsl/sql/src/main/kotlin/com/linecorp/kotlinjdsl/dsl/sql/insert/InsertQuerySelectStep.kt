package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable
import com.linecorp.kotlinjdsl.querymodel.sql.Table

interface InsertQuerySelectStep<T : Any> : SqlQueryable<InsertQuery<T>> {
    fun select(table: Table<*>): SqlQueryable<InsertQuery<T>>
    fun select(select: SelectQuery): SqlQueryable<InsertQuery<T>>
    fun select(select: SqlQueryable<SelectQuery>): SqlQueryable<InsertQuery<T>>
}
