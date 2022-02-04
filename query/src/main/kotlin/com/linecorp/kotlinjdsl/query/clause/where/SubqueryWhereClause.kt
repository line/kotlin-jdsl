package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

interface SubqueryWhereClause {
    fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder)
}