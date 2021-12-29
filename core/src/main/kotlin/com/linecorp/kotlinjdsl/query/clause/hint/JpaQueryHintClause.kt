package com.linecorp.kotlinjdsl.query.clause.hint

import javax.persistence.Query

interface JpaQueryHintClause {
    fun apply(query: Query)
}