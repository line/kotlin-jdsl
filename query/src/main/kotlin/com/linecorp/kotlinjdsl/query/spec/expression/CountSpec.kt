package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

data class CountSpec<T>(
    val distinct: Boolean = false,
    val expression: ExpressionSpec<T>,
) : ExpressionSpec<Long> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Long> {
        val jpaExpression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaExpression(criteriaBuilder, jpaExpression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Long> {
        val jpaExpression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaExpression(criteriaBuilder, jpaExpression)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Long> {
        val jpaExpression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaExpression(criteriaBuilder, jpaExpression)
    }

    private fun toCriteriaExpression(
        criteriaBuilder: CriteriaBuilder,
        jpaExpression: Expression<T>
    ): Expression<Long> {
        return if (distinct) {
            criteriaBuilder.countDistinct(jpaExpression)
        } else {
            criteriaBuilder.count(jpaExpression)
        }
    }
}

