package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

data class BetweenValueSpec<T, R>(
    val left: ExpressionSpec<T>,
    val right1: R,
    val right2: R,
) : PredicateSpec where R : Comparable<R>, R : Any, T : R? {
    @Suppress("TYPE_MISMATCH_WARNING")
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between<R>(left.toCriteriaExpression(froms, query, criteriaBuilder), right1, right2)
    }
}