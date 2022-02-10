package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Expression

data class GreatestSpec<T : Comparable<T>?>(
    val column: ColumnSpec<T>
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        val expression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.greatest(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        val expression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.greatest(expression)
    }
}
