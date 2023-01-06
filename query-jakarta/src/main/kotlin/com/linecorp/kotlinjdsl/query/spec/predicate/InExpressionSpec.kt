package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.*

data class InExpressionSpec<T>(
    private val left: ExpressionSpec<T>,
    private val rights: Collection<ExpressionSpec<T>>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        if (rights.isEmpty()) return criteriaBuilder.conjunction()

        return criteriaBuilder.`in`(left.toCriteriaExpression(froms, query, criteriaBuilder)).apply {
            rights.forEach { value(it.toCriteriaExpression(froms, query, criteriaBuilder)) }
        }
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        if (rights.isEmpty()) return criteriaBuilder.conjunction()

        return criteriaBuilder.`in`(left.toCriteriaExpression(froms, query, criteriaBuilder)).apply {
            rights.forEach { value(it.toCriteriaExpression(froms, query, criteriaBuilder)) }
        }
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        if (rights.isEmpty()) return criteriaBuilder.conjunction()

        return criteriaBuilder.`in`(left.toCriteriaExpression(froms, query, criteriaBuilder)).apply {
            rights.forEach { value(it.toCriteriaExpression(froms, query, criteriaBuilder)) }
        }
    }
}
