package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import javax.persistence.criteria.*

data class ExistsSpec<T>(
    private val subqueryExpressionSpec: SubqueryExpressionSpec<T>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val subQueryExpression = subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
        subQueryExpression as Subquery<T>
        return criteriaBuilder.exists(subQueryExpression)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val subQueryExpression = subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
        subQueryExpression as Subquery<T>
        return criteriaBuilder.exists(subQueryExpression)
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val subQueryExpression = subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
        subQueryExpression as Subquery<T>
        return criteriaBuilder.exists(subQueryExpression)
    }
}
