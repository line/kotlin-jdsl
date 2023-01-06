package com.linecorp.kotlinjdsl.query.spec

import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Order

data class ExpressionOrderSpec(
    private val expression: ExpressionSpec<*>,
    private val ascending: Boolean,
) : OrderSpec {
    override fun toCriteriaOrder(
        froms: Froms,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): List<Order> = listOf(
        if (ascending) {
            criteriaBuilder.asc(expression.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder))
        } else {
            criteriaBuilder.desc(expression.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder))
        }
    )
}
