package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
data class SingleSelectClause<T>(
    override val returnType: Class<T>,
    val distinct: Boolean,
    val expression: ExpressionSpec<T>,
) : CriteriaQuerySelectClause<T>, SubquerySelectClause<T> {
    override fun apply(froms: Froms, query: CriteriaQuery<T>, criteriaBuilder: CriteriaBuilder) {
        query
            .select(expression.toCriteriaExpression(froms, query, criteriaBuilder))
            .distinct(distinct)
    }

    override fun apply(froms: Froms, query: Subquery<T>, criteriaBuilder: CriteriaBuilder) {
        query
            .select(expression.toCriteriaExpression(froms, query, criteriaBuilder))
            .distinct(distinct)
    }
}
