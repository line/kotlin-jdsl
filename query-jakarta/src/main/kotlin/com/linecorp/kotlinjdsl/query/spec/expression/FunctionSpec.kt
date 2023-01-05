package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.*

data class FunctionSpec<T>(
    private val name: String,
    private val returnType: Class<T>,
    private val expressions: List<ExpressionSpec<*>>,
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return toCriteriaExpression(
            criteriaBuilder = criteriaBuilder,
            expressions = expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }
        )
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return toCriteriaExpression(
            criteriaBuilder = criteriaBuilder,
            expressions = expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }
        )
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return toCriteriaExpression(
            criteriaBuilder = criteriaBuilder,
            expressions = expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }
        )
    }

    private fun toCriteriaExpression(
        criteriaBuilder: CriteriaBuilder,
        expressions: List<Expression<out Any?>>
    ): Expression<T> {
        return criteriaBuilder.function(
            name,
            returnType,
            *expressions.toTypedArray()
        )
    }
}
