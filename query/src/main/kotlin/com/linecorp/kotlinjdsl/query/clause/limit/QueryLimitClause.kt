package com.linecorp.kotlinjdsl.query.clause.limit

import javax.persistence.Query

interface QueryLimitClause {
    fun apply(query: Query)
}