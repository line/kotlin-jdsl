package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.*

data class BetweenValueSpec<T, R>(
    private val left: ExpressionSpec<T>,
    private val right1: R,
    private val right2: R,
) : PredicateSpec where R : Comparable<R>, R : Any, T : R? {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(left.toCriteriaExpression(froms, query, criteriaBuilder), right1, right2)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(left.toCriteriaExpression(froms, query, criteriaBuilder), right1, right2)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(left.toCriteriaExpression(froms, query, criteriaBuilder), right1, right2)
    }
}
