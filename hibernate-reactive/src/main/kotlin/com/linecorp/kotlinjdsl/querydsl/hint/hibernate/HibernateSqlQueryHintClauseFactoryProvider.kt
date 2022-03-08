package com.linecorp.kotlinjdsl.querydsl.hint.hibernate

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlReactiveQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlReactiveQueryHintClauseFactoryProvider

class HibernateSqlQueryHintClauseFactoryProvider : SqlReactiveQueryHintClauseFactoryProvider {
    override fun <T> factory(): (List<String>) -> SqlQueryHintClause<ReactiveQuery<T>> =
        { HibernateSqlReactiveQueryHintClause(it) }

}
