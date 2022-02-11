package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

data class LeastSpec<T : Comparable<T>?>(
    val column: ColumnSpec<T>
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        val expression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.least(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        val expression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.least(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        val expression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.least(expression)
    }
}
