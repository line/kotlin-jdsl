package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.*

data class IsFalseSpec(
    val expression: ExpressionSpec<out Boolean?>
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        @Suppress("UNCHECKED_CAST")
        return criteriaBuilder.isFalse(
            expression.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<Boolean?>
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        @Suppress("UNCHECKED_CAST")
        return criteriaBuilder.isFalse(
            expression.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<Boolean?>
        )
    }
}
