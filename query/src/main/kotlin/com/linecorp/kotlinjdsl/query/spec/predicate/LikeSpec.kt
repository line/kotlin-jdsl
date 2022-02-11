package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.*

@Suppress("UNCHECKED_CAST")
data class LikeSpec(
    val left: ExpressionSpec<out String?>,
    val right: String
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.like(
            left.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<String?>,
            right
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.like(
            left.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<String?>,
            right
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.like(
            left.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<String?>,
            right
        )
    }
}
