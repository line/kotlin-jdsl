package com.linecorp.kotlinjdsl.query.clause.having

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
data class HavingClause(
    private val predicate: PredicateSpec,
) : CriteriaQueryHavingClause, SubqueryHavingClause {
    override fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    override fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    private fun applyInternally(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder) {
        if (predicate.isEmpty()) return

        query.having(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }
}
