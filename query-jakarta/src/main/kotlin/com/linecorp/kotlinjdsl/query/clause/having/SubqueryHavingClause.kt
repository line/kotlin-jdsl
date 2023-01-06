package com.linecorp.kotlinjdsl.query.clause.having

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface SubqueryHavingClause {
    fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder)
}
