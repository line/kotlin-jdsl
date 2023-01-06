package com.linecorp.kotlinjdsl.querydsl.select

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec

/**
 * This is Dsl which is used to select a single entity or a single column.
 */
interface SingleSelectDsl<T> {
    fun select(distinct: Boolean, entity: Class<T>) = select(distinct, EntitySpec(entity))
    fun select(distinct: Boolean, expression: ExpressionSpec<T>): SingleSelectClause<T>

    fun select(entity: Class<T>) = select(distinct = false, entity)
    fun select(expression: ExpressionSpec<T>) = select(distinct = false, expression)

    fun selectDistinct(entity: Class<T>) = select(distinct = true, entity)
    fun selectDistinct(expression: ExpressionSpec<T>) = select(distinct = true, expression)
}
