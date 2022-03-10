package com.linecorp.kotlinjdsl.querydsl.hint

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.emptySqlHintClause
import org.slf4j.LoggerFactory
import java.util.*

interface SqlReactiveQueryHintClauseFactoryProvider {
    fun <T> factory(): (List<String>) -> SqlQueryHintClause<ReactiveQuery<T>>

    companion object {
        private val log = LoggerFactory.getLogger(SqlReactiveQueryHintClauseFactoryProvider::class.java)
        val loadedFactory by lazy {
            ServiceLoader.load(SqlReactiveQueryHintClauseFactoryProvider::class.java).checkDuplicate().firstOrNull()
                ?.factory<Any>()
        }

        private fun ServiceLoader<SqlReactiveQueryHintClauseFactoryProvider>.checkDuplicate(): ServiceLoader<SqlReactiveQueryHintClauseFactoryProvider> {
            takeIf { count() > 1 }
                ?.run { log.warn("Duplicate Factory implementation detected") }
            return this
        }
    }
}

object SqlQueryHintClauseProvider {
    @Suppress("UNCHECKED_CAST")
    fun <T> provide(hints: List<String>): SqlQueryHintClause<ReactiveQuery<T>> {
        val factory = (SqlReactiveQueryHintClauseFactoryProvider.loadedFactory
            ?: { emptySqlHintClause() }) as (List<String>) -> SqlQueryHintClause<ReactiveQuery<T>>
        return factory(hints)
    }
}
