package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

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
