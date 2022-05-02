package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface CriteriaQuerySelectClause<T> {
    val returnType: Class<T>

    fun apply(froms: Froms, query: CriteriaQuery<T>, criteriaBuilder: CriteriaBuilder)
}
