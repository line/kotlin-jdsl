package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaDelete
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.CriteriaUpdate

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface CriteriaQueryWhereClause {
    fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder)
    fun apply(froms: Froms, query: CriteriaUpdate<*>, criteriaBuilder: CriteriaBuilder)
    fun apply(froms: Froms, query: CriteriaDelete<*>, criteriaBuilder: CriteriaBuilder)
}
