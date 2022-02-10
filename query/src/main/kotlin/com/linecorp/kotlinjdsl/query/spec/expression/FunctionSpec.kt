package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Expression

data class FunctionSpec<T>(
    val name: String,
    val returnType: Class<T>,
    val expressions: List<ExpressionSpec<*>>,
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return toCriteriaExpression(
            criteriaBuilder = criteriaBuilder,
            expressions = expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }.toTypedArray()
        )
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return toCriteriaExpression(
            criteriaBuilder = criteriaBuilder,
            expressions = expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }.toTypedArray()
        )
    }

    private fun toCriteriaExpression(
        criteriaBuilder: CriteriaBuilder,
        expressions: Array<Expression<out Any?>>
    ): Expression<T> {
        return criteriaBuilder.function(
            name,
            returnType,
            *expressions
        )
    }
}
