package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.*

data class BetweenExpressionSpec<T : Comparable<T>>(
    private val left: ExpressionSpec<T>,
    private val right1: ExpressionSpec<T>,
    private val right2: ExpressionSpec<T>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(
            left.toCriteriaExpression(froms, query, criteriaBuilder),
            right1.toCriteriaExpression(froms, query, criteriaBuilder),
            right2.toCriteriaExpression(froms, query, criteriaBuilder),
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(
            left.toCriteriaExpression(froms, query, criteriaBuilder),
            right1.toCriteriaExpression(froms, query, criteriaBuilder),
            right2.toCriteriaExpression(froms, query, criteriaBuilder),
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(
            left.toCriteriaExpression(froms, query, criteriaBuilder),
            right1.toCriteriaExpression(froms, query, criteriaBuilder),
            right2.toCriteriaExpression(froms, query, criteriaBuilder),
        )
    }
}
