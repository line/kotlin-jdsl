package com.linecorp.kotlinjdsl.querydsl.select

import com.linecorp.kotlinjdsl.query.clause.select.MultiSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec

/**
 * This is Dsl which is used to select multiple column.
 *
 * **notice**: vararg is not supported with method name: select.
 * This is because wrong type inference is made when SingleSelectDsl and MultiSelectDsl are used together.
 */
interface MultiSelectDsl<T> {
    fun select(distinct: Boolean, expressions: List<ExpressionSpec<*>>): MultiSelectClause<T>
    fun select(expressions: List<ExpressionSpec<*>>) = select(distinct = false, expressions)
    fun selectDistinct(expressions: List<ExpressionSpec<*>>) = select(distinct = true, expressions)

    fun selectMulti(distinct: Boolean, expressions: List<ExpressionSpec<*>>) = select(distinct, expressions)
    fun selectMulti(vararg expressions: ExpressionSpec<*>) = select(distinct = false, expressions.toList())
    fun selectMulti(expressions: List<ExpressionSpec<*>>) = select(distinct = false, expressions)
    fun selectDistinctMulti(vararg expressions: ExpressionSpec<*>) = select(distinct = true, expressions.toList())
    fun selectDistinctMulti(expressions: List<ExpressionSpec<*>>) = select(distinct = true, expressions)
}
