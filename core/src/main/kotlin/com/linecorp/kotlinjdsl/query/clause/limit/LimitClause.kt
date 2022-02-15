package com.linecorp.kotlinjdsl.query.clause.limit

import javax.persistence.Query

data class LimitClause(
    val offset: Int?,
    val maxResults: Int?,
) : QueryLimitClause<Query> {
    companion object {
        val empty = LimitClause(null, null)
    }

    override fun apply(query: Query) {
        offset?.run { query.firstResult = this }
        maxResults?.run { query.maxResults = this }
    }
}
