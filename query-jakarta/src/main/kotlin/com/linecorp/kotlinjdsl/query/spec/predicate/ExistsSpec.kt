package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import jakarta.persistence.criteria.*

data class ExistsSpec<T>(
    private val subqueryExpressionSpec: SubqueryExpressionSpec<T>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val subqueryExpression = subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
        return criteriaBuilder.exists(subqueryExpression as Subquery<T>)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val subqueryExpression = subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
        return criteriaBuilder.exists(subqueryExpression as Subquery<T>)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val subqueryExpression = subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
        return criteriaBuilder.exists(subqueryExpression as Subquery<T>)
    }
}
