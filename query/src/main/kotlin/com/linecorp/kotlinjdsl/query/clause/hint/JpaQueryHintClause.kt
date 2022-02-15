package com.linecorp.kotlinjdsl.query.clause.hint

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface JpaQueryHintClause<Q> {
    fun apply(query: Q)
}
