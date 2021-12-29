package com.linecorp.kotlinjdsl.query.clause.hint

import org.slf4j.LoggerFactory
import javax.persistence.Query

object EmptySqlQueryHintClause : SqlQueryHintClause {
    private val log = LoggerFactory.getLogger(EmptySqlQueryHintClause::class.java)

    override fun apply(query: Query) {
        log.warn(
            "We do not yet support the sql query hint of this jpa implementation. Please contact us through github."
        )
    }
}