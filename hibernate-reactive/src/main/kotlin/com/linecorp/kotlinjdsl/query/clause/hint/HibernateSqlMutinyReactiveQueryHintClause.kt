package com.linecorp.kotlinjdsl.query.clause.hint

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import org.hibernate.reactive.mutiny.Mutiny
import org.slf4j.LoggerFactory

data class HibernateSqlMutinyReactiveQueryHintClause<R>(
    private val queryHints: List<String>
) : SqlQueryHintClause<ReactiveQuery<R>> {
    companion object {
        private val log = LoggerFactory.getLogger(HibernateSqlMutinyReactiveQueryHintClause::class.java)
    }

    override fun apply(query: ReactiveQuery<R>) {
        if (queryHints.isEmpty()) return

        val unwrappedQuery = try {
            query.unwrap(Mutiny.Query::class)
        } catch (e: Exception) {
            log.error("This query does not support hibernate query hint", e)
            null
        }

        unwrappedQuery?.run {
            queryHints.forEach { setComment(it) }
        }
    }
}
