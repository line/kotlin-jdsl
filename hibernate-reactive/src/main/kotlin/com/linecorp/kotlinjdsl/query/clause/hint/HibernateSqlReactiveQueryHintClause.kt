package com.linecorp.kotlinjdsl.query.clause.hint

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import org.hibernate.reactive.stage.Stage
import org.slf4j.LoggerFactory

data class HibernateSqlReactiveQueryHintClause<R>(
    val queryHints: List<String>
) : SqlQueryHintClause<ReactiveQuery<R>> {
    companion object {
        private val log = LoggerFactory.getLogger(HibernateSqlReactiveQueryHintClause::class.java)
    }

    override fun apply(query: ReactiveQuery<R>) {
        if (queryHints.isEmpty()) return

        val unwrappedQuery = try {
            query.unwrap(Stage.Query::class)
        } catch (e: Exception) {
            log.error("This query does not support hibernate query hint", e)
            null
        }

        unwrappedQuery?.run {
            queryHints.forEach { setComment(it) }
        }
    }
}
