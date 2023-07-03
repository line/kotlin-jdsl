package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.InsertQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

interface InsertQueryValueStepN<T : Any> :
    InsertQuerySelectStep<T>,
    SqlQueryable<InsertQuery<T>> {

    fun values(
        vararg values: Any?,
    ): InsertQueryValueStepN<T>

    fun values(
        vararg values: Expression<*>,
    ): InsertQueryValueStepN<T>
}
