package com.linecorp.kotlinjdsl.query.clause.limit

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface QueryLimitClause<Q> {
    fun apply(query: Q)
}
