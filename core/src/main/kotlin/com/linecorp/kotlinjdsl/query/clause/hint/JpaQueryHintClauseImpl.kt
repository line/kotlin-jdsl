package com.linecorp.kotlinjdsl.query.clause.hint

import javax.persistence.Query

data class JpaQueryHintClauseImpl(
    val hints: Map<String, Any>
) : JpaQueryHintClause<Query> {
    override fun apply(query: Query) {
        hints.forEach { (hintName, value) -> query.setHint(hintName, value) }
    }
}
