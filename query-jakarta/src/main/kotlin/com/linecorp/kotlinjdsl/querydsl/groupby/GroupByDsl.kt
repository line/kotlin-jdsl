package com.linecorp.kotlinjdsl.querydsl.groupby

import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec

interface GroupByDsl {
    fun groupBy(vararg columns: ExpressionSpec<*>) = groupBy(columns.toList())
    fun groupBy(columns: List<ExpressionSpec<*>>)
}
