package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

data class WhereClause(
    val predicate: PredicateSpec
) : CriteriaQueryWhereClause, SubqueryWhereClause {
    override fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    override fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    private fun applyInternally(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder) {
        if (predicate.isEmpty()) return

        query.where(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }
}