package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.query.sql.InsertQuery
import com.linecorp.kotlinjdsl.query.sql.SelectQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable
import com.linecorp.kotlinjdsl.query.sql.Table

interface InsertQuerySelectStep<T : Any> : SqlQueryable<InsertQuery<T>> {
    fun select(table: Table<*>): SqlQueryable<InsertQuery<T>>
    fun select(select: SelectQuery): SqlQueryable<InsertQuery<T>>
    fun select(select: SqlQueryable<SelectQuery>): SqlQueryable<InsertQuery<T>>
}
