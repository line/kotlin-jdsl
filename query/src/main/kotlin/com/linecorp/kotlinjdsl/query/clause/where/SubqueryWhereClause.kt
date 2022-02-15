package com.linecorp.kotlinjdsl.query.clause.where

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface SubqueryWhereClause {
    fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder)
}
