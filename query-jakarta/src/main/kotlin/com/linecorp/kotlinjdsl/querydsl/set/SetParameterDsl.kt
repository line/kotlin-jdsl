package com.linecorp.kotlinjdsl.querydsl.set

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec

interface SetParameterDsl {
    fun setParams(vararg params: Pair<ColumnSpec<*>, Any?>) = setParams(params.toMap())
    fun setParams(params: Map<ColumnSpec<*>, Any?>)
    fun set(column: ColumnSpec<*>, value: Any?)
}
