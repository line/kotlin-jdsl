package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.CriteriaUpdate

interface CriteriaQueryWhereClause {
    fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder)
    fun apply(froms: Froms, query: CriteriaUpdate<*>, criteriaBuilder: CriteriaBuilder)
}
