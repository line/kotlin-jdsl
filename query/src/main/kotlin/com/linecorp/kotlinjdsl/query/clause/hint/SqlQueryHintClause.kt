package com.linecorp.kotlinjdsl.query.clause.hint

import javax.persistence.Query

interface SqlQueryHintClause {
    fun apply(query: Query)
}