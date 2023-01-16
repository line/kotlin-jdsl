package com.linecorp.kotlinjdsl.query.clause.hint

import com.linecorp.kotlinjdsl.query.clause.hint.EmptySqlReactiveQueryHintClause.Companion.empty
import org.slf4j.LoggerFactory

@Suppress("UNCHECKED_CAST")
inline fun <reified T> emptySqlHintClause(): SqlQueryHintClause<T> = empty as SqlQueryHintClause<T>

class EmptySqlReactiveQueryHintClause<T> : SqlQueryHintClause<T> {
    private val log = LoggerFactory.getLogger(EmptySqlReactiveQueryHintClause::class.java)

    companion object {
        val empty: SqlQueryHintClause<*> = EmptySqlReactiveQueryHintClause<Any>()
    }

    override fun apply(query: T) {
        log.warn(
            "We do not yet support the sql query hint of this jpa implementation. Please contact us through github."
        )
    }
}
