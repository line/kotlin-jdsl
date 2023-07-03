package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.InsertQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

interface InsertQueryValueStep11<T : Any, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11> :
    InsertQuerySelectStep<T>,
    SqlQueryable<InsertQuery<T>> {

    fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11>

    fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11>

    fun values(
        values: Collection<Expression<*>>,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11>
}
