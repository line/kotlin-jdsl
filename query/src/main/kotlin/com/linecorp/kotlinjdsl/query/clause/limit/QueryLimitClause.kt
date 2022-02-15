package com.linecorp.kotlinjdsl.query.clause.limit

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface QueryLimitClause<Q> {
    fun apply(query: Q)
}
