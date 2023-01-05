package com.linecorp.kotlinjdsl.querydsl.hint

import com.linecorp.kotlinjdsl.query.clause.hint.EmptySqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import org.slf4j.LoggerFactory
import java.util.*
import jakarta.persistence.Query

interface SqlQueryHintClauseFactoryProvider {
    fun factory(): (List<String>) -> SqlQueryHintClause<Query>

    companion object {
        private val log = LoggerFactory.getLogger(SqlQueryHintClauseFactoryProvider::class.java)
        val loadedFactory by lazy {
            ServiceLoader.load(SqlQueryHintClauseFactoryProvider::class.java).checkDuplicate().firstOrNull()?.factory()
        }

        private fun ServiceLoader<SqlQueryHintClauseFactoryProvider>.checkDuplicate(): ServiceLoader<SqlQueryHintClauseFactoryProvider> {
            takeIf { count() > 1 }
                ?.run { log.warn("Duplicate Factory implementation detected") }
            return this
        }
    }
}

object SqlQueryHintClauseProvider {
    val provide: (List<String>) -> SqlQueryHintClause<Query>

    init {
        val factory = SqlQueryHintClauseFactoryProvider.loadedFactory ?: { EmptySqlQueryHintClause }
        provide = { factory.invoke(it) }
    }
}
