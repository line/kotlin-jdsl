package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.*

data class GreatestSpec<T : Comparable<T>?>(
    private val expression: ExpressionSpec<T>
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<out T> {
        val expression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.greatest(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<out T> {
        val expression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.greatest(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<out T> {
        val expression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.greatest(expression)
    }
}
