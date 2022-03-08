package com.linecorp.kotlinjdsl.query.clause.limit

import com.linecorp.kotlinjdsl.query.ReactiveQuery


data class ReactiveLimitClause<R>(
    val offset: Int?,
    val maxResults: Int?,
) : QueryLimitClause<ReactiveQuery<R>> {
    companion object {
        val empty = ReactiveLimitClause<Any>(null, null)
        @Suppress("UNCHECKED_CAST")
        fun <T> empty(): QueryLimitClause<T> = empty as QueryLimitClause<T>
    }

    override fun apply(query: ReactiveQuery<R>) {
        offset?.run { query.setFirstResult(this) }
        maxResults?.run { query.setMaxResults(this) }
    }
}
