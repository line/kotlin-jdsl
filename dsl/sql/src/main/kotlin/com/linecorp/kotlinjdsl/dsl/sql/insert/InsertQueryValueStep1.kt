package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

interface InsertQueryValueStep1<T : Any, V1> :
    InsertQuerySelectStep<T>,
    SqlQueryable<InsertQuery<T>> {

    fun values(
        value1: V1,
    ): InsertQueryValueStep1<T, V1>

    fun values(
        value1: Expression<V1>,
    ): InsertQueryValueStep1<T, V1>

    fun values(
        values: Iterable<Expression<*>>,
    ): InsertQueryValueStep1<T, V1>
}
