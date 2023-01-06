package com.linecorp.kotlinjdsl.query.clause.hint

import jakarta.persistence.Query

data class JpaQueryHintClauseImpl<Q : Query>(
    val hints: Map<String, Any>
) : JpaQueryHintClause<Q> {
    override fun apply(query: Q) {
        hints.forEach { (hintName, value) -> query.setHint(hintName, value) }
    }
}
