package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface SubquerySelectClause<T> {
    val returnType: Class<T>

    fun apply(froms: Froms, query: Subquery<T>, criteriaBuilder: CriteriaBuilder)
}
