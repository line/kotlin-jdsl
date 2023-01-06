package com.linecorp.kotlinjdsl.querydsl.orderby

import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec

interface OrderByDsl {
    fun orderBy(vararg orders: OrderSpec) = orderBy(orders.toList())
    fun orderBy(orders: List<OrderSpec>)

    fun ExpressionSpec<*>.asc(): OrderSpec = ExpressionOrderSpec(expression = this, ascending = true)
    fun ExpressionSpec<*>.desc(): OrderSpec = ExpressionOrderSpec(expression = this, ascending = false)
}
