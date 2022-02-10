package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import javax.persistence.criteria.*

data class WhereClause(
    val predicate: PredicateSpec
) : CriteriaQueryWhereClause, SubqueryWhereClause {
    override fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    override fun apply(froms: Froms, query: CriteriaUpdate<*>, criteriaBuilder: CriteriaBuilder) {
        if (predicate.isEmpty()) return

        query.where(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }

    override fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    private fun applyInternally(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder) {
        if (predicate.isEmpty()) return

        query.where(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }
}
