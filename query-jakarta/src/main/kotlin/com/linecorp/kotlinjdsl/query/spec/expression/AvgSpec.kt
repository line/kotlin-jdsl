package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.*

data class AvgSpec<T : Number?>(
    private val expression: ExpressionSpec<T>
) : ExpressionSpec<Double> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Double> {
        val expression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.avg(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Double> {
        val expression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.avg(expression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Double> {
        val expression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return criteriaBuilder.avg(expression)
    }
}
