package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

data class InValueSpec<T>(
    val left: ExpressionSpec<T>,
    val rights: Collection<T>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        if (rights.isEmpty()) return criteriaBuilder.conjunction()

        return criteriaBuilder.`in`(left.toCriteriaExpression(froms, query, criteriaBuilder)).apply {
            rights.forEach { value(it) }
        }
    }
}