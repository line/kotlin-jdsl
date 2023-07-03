package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.InsertQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

interface InsertQueryValueStep2<T : Any, V1, V2> :
    InsertQuerySelectStep<T>,
    SqlQueryable<InsertQuery<T>> {

    fun values(
        value1: V1,
        value2: V2,
    ): InsertQueryValueStep2<T, V1, V2>

    fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
    ): InsertQueryValueStep2<T, V1, V2>

    fun values(
        values: Collection<Expression<*>>,
    ): InsertQueryValueStep2<T, V1, V2>
}
