package com.linecorp.kotlinjdsl.dsl.sql.insert

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

interface InsertQueryValueStep21<T : Any, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21> :
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
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
        value18: V18,
        value19: V19,
        value20: V20,
        value21: V21,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21>

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
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
        value18: Expression<V18>,
        value19: Expression<V19>,
        value20: Expression<V20>,
        value21: Expression<V21>,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21>

    fun values(
        values: Iterable<Expression<*>>,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21>
}
