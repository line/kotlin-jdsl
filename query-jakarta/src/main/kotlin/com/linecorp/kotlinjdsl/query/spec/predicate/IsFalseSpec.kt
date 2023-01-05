package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.*

@Suppress("UNCHECKED_CAST")
data class IsFalseSpec(
    private val expression: ExpressionSpec<out Boolean?>
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.isFalse(
            expression.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<Boolean?>
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.isFalse(
            expression.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<Boolean?>
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.isFalse(
            expression.toCriteriaExpression(froms, query, criteriaBuilder) as Expression<Boolean?>
        )
    }
}
