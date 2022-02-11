package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.*

data class LessThanExpressionSpec<T : Comparable<T>>(
    val left: ExpressionSpec<T>,
    val right: ExpressionSpec<T>,
    val inclusive: Boolean,
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
        leftExpression: Expression<T>,
        rightExpression: Expression<T>
    ): Predicate {
        return if (inclusive) {
            criteriaBuilder.lessThanOrEqualTo(leftExpression, rightExpression)
        } else {
            criteriaBuilder.lessThan(leftExpression, rightExpression)
        }
    }
}
