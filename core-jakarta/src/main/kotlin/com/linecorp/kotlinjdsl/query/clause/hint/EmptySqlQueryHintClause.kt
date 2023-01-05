package com.linecorp.kotlinjdsl.query.clause.hint

import org.slf4j.LoggerFactory
import jakarta.persistence.Query

@Suppress("UNCHECKED_CAST")
inline fun <reified T> emptySqlHintClause(): SqlQueryHintClause<T> = EmptySqlQueryHintClause as SqlQueryHintClause<T>

object EmptySqlQueryHintClause : SqlQueryHintClause<Query> {
    private val log = LoggerFactory.getLogger(EmptySqlQueryHintClause::class.java)

    override fun apply(query: Query) {
        log.warn(
            "We do not yet support the sql query hint of this jpa implementation. Please contact us through github."
        )
    }
}
