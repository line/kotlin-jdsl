package com.linecorp.kotlinjdsl.querydsl.hint.hibernate

import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlQueryHintClauseFactoryProvider

class HibernateSqlQueryHintClauseFactoryProvider : SqlQueryHintClauseFactoryProvider {
    override fun factory(): (List<String>) -> SqlQueryHintClause = { HibernateSqlQueryHintClause(it) }
}
