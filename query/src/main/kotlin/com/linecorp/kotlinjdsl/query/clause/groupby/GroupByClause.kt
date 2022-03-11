package com.linecorp.kotlinjdsl.query.clause.groupby

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
data class GroupByClause(
    private val expressions: List<ExpressionSpec<*>>,
) : CriteriaQueryGroupByClause, SubqueryGroupByClause {
    override fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    override fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    private fun applyInternally(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder) {
        if (expressions.isEmpty()) return

        query.groupBy(expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) })
    }
}
