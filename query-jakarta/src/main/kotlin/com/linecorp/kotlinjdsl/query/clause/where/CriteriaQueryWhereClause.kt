package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaDelete
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.CriteriaUpdate

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface CriteriaQueryWhereClause {
    fun <T> apply(froms: Froms, query: CriteriaQuery<T>, criteriaBuilder: CriteriaBuilder)
    fun <T> apply(froms: Froms, query: CriteriaUpdate<T>, criteriaBuilder: CriteriaBuilder)
    fun <T> apply(froms: Froms, query: CriteriaDelete<T>, criteriaBuilder: CriteriaBuilder)
}
