package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.*

data class GreaterThanExpressionSpec<T : Comparable<T>>(
    private val left: ExpressionSpec<T>,
    private val right: ExpressionSpec<T>,
    private val inclusive: Boolean,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression = left.toCriteriaExpression(froms, query, criteriaBuilder)
        val rightExpression = right.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaPredicate(criteriaBuilder, leftExpression, rightExpression)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression = left.toCriteriaExpression(froms, query, criteriaBuilder)
        val rightExpression = right.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaPredicate(criteriaBuilder, leftExpression, rightExpression)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression = left.toCriteriaExpression(froms, query, criteriaBuilder)
        val rightExpression = right.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaPredicate(criteriaBuilder, leftExpression, rightExpression)
    }

    private fun toCriteriaPredicate(
        criteriaBuilder: CriteriaBuilder,
        leftExpression: Expression<out T>,
        rightExpression: Expression<out T>
    ): Predicate {
        return if (inclusive) {
            criteriaBuilder.greaterThanOrEqualTo(leftExpression, rightExpression)
        } else {
            criteriaBuilder.greaterThan(leftExpression, rightExpression)
        }
    }
}
