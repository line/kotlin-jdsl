package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import jakarta.persistence.criteria.*

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
data class WhereClause(
    private val predicate: PredicateSpec
) : CriteriaQueryWhereClause, SubqueryWhereClause {
    override fun <T> apply(froms: Froms, query: CriteriaQuery<T>, criteriaBuilder: CriteriaBuilder) {
        applyInternally(froms, query, criteriaBuilder)
    }

    override fun <T> apply(froms: Froms, query: CriteriaUpdate<T>, criteriaBuilder: CriteriaBuilder) {
        if (predicate.isEmpty()) return

        query.where(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }

    override fun <T> apply(froms: Froms, query: CriteriaDelete<T>, criteriaBuilder: CriteriaBuilder) {
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
