package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression

data class AvgSpec<T : Number?>(
    val column: ColumnSpec<T>
) : ExpressionSpec<Double> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Double> {
        val expression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.avg(expression)
    }
}