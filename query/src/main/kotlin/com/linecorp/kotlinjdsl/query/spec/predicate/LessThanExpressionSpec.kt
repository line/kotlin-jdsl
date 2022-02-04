package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

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

        return if (inclusive) {
            criteriaBuilder.lessThanOrEqualTo(leftExpression, rightExpression)
        } else {
            criteriaBuilder.lessThan(leftExpression, rightExpression)
        }
    }
}
