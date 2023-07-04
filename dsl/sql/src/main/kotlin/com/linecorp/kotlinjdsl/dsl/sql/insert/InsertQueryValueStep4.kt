package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

interface InsertQueryValueStep4<T : Any, V1, V2, V3, V4> :
    InsertQuerySelectStep<T>,
    SqlQueryable<InsertQuery<T>> {

    fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4>

    fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4>

    fun values(
        values: Collection<Expression<*>>,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4>
}
