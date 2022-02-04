package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Predicate

data class LikeSpec(
    val left: ExpressionSpec<out String?>,
    val right: String
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        @Suppress("UNCHECKED_CAST")
        return criteriaBuilder.like(
            left.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<String?>,
            right
        )
    }
}