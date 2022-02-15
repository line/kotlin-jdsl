package com.linecorp.kotlinjdsl.query.clause.groupby

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface CriteriaQueryGroupByClause {
    fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder)
}
