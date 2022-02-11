package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.*

data class EqualExpressionSpec<T>(
    val left: ExpressionSpec<T>,
    val right: ExpressionSpec<T>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.equal(
            left.toCriteriaExpression(froms, query, criteriaBuilder),
            right.toCriteriaExpression(froms, query, criteriaBuilder),
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.equal(
            left.toCriteriaExpression(froms, query, criteriaBuilder),
            right.toCriteriaExpression(froms, query, criteriaBuilder),
        )
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.equal(
            left.toCriteriaExpression(froms, query, criteriaBuilder),
            right.toCriteriaExpression(froms, query, criteriaBuilder),
        )
    }
}
