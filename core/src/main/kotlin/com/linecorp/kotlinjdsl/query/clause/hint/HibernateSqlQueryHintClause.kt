package com.linecorp.kotlinjdsl.query.clause.hint

import org.slf4j.LoggerFactory
import javax.persistence.Query

data class HibernateSqlQueryHintClause(
    val queryHints: List<String>
) : SqlQueryHintClause {
    companion object {
        private val log = LoggerFactory.getLogger(HibernateSqlQueryHintClause::class.java)
    }

    override fun apply(query: Query) {
        if (queryHints.isEmpty()) return

        val unwrappedQuery = try {
            query.unwrap(org.hibernate.query.Query::class.java)
        } catch (e: Exception) {
            log.error("This query does not support hibernate query hint", e)
            null
        }

        unwrappedQuery?.run {
            queryHints.forEach { addQueryHint(it) }
        }
    }
}