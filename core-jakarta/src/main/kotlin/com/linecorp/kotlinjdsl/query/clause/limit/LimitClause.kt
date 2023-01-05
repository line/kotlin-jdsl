package com.linecorp.kotlinjdsl.query.clause.limit

import jakarta.persistence.Query

data class LimitClause<Q : Query>(
    private val offset: Int?,
    private val maxResults: Int?,
) : QueryLimitClause<Q> {
    companion object {
        val empty = LimitClause<Query>(null, null)

        @Suppress("UNCHECKED_CAST")
        fun <T : Query> empty(): LimitClause<T> = empty as LimitClause<T>
    }

    override fun apply(query: Q) {
        offset?.run { query.firstResult = this }
        maxResults?.run { query.maxResults = this }
    }
}
