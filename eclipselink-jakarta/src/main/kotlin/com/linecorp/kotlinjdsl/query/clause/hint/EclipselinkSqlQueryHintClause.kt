package com.linecorp.kotlinjdsl.query.clause.hint

import org.eclipse.persistence.queries.DatabaseQuery
import org.slf4j.LoggerFactory
import jakarta.persistence.Query

data class EclipselinkSqlQueryHintClause(
    private val queryHints: List<String>
) : SqlQueryHintClause<Query> {
    companion object {
        private val log = LoggerFactory.getLogger(EclipselinkSqlQueryHintClause::class.java)
    }

    override fun apply(query: Query) {
        if (queryHints.isEmpty()) return

        val unwrappedQuery = try {
            query.unwrap(DatabaseQuery::class.java)
        } catch (e: Exception) {
            log.error("This query does not support eclipselink query hint", e)
            null
        }

        unwrappedQuery?.run {
            queryHints.forEach { hintString = it }
        }
    }
}
