package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Predicate

data class LessThanValueSpec<T, R>(
    val left: ExpressionSpec<T>,
    val right: R,
    val inclusive: Boolean,
) : PredicateSpec where R : Comparable<R>, R : Any, T : R? {
    @Suppress("TYPE_MISMATCH_WARNING")
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression: Expression<T> = left.toCriteriaExpression(froms, query, criteriaBuilder)

        return if (inclusive) {
            criteriaBuilder.lessThanOrEqualTo<R>(leftExpression, right)
        } else {
            criteriaBuilder.lessThan<R>(leftExpression, right)
        }
    }
}