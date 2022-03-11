package com.linecorp.kotlinjdsl.querydsl.hint.hibernate

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlMutinyReactiveQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlReactiveQueryHintClauseFactoryProvider

class HibernateSqlReactiveQueryHintClauseFactoryProvider : SqlReactiveQueryHintClauseFactoryProvider {
    override fun <T> factory(): (List<String>) -> SqlQueryHintClause<ReactiveQuery<T>> =
        { HibernateSqlMutinyReactiveQueryHintClause(it) }
}
