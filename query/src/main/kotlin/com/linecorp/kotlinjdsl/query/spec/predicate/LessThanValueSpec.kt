package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.*

@Suppress("TYPE_MISMATCH_WARNING")
data class LessThanValueSpec<T, R>(
    val left: ExpressionSpec<T>,
    val right: R,
    val inclusive: Boolean,
) : PredicateSpec where R : Comparable<R>, R : Any, T : R? {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression: Expression<T> = left.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaPredicate(criteriaBuilder, leftExpression)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression: Expression<T> = left.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaPredicate(criteriaBuilder, leftExpression)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val leftExpression: Expression<T> = left.toCriteriaExpression(froms, query, criteriaBuilder)

        return toCriteriaPredicate(criteriaBuilder, leftExpression)
    }

    private fun toCriteriaPredicate(
        criteriaBuilder: CriteriaBuilder,
        leftExpression: Expression<T>
    ): Predicate {
        return if (inclusive) {
            criteriaBuilder.lessThanOrEqualTo<R>(leftExpression, right)
        } else {
            criteriaBuilder.lessThan<R>(leftExpression, right)
        }
    }
}
