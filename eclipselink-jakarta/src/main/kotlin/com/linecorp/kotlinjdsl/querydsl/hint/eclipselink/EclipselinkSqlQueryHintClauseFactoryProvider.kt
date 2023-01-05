package com.linecorp.kotlinjdsl.querydsl.hint.eclipselink

import com.linecorp.kotlinjdsl.query.clause.hint.EclipselinkSqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlQueryHintClauseFactoryProvider
import jakarta.persistence.Query

class EclipselinkSqlQueryHintClauseFactoryProvider : SqlQueryHintClauseFactoryProvider {
    override fun factory(): (List<String>) -> SqlQueryHintClause<Query> = { EclipselinkSqlQueryHintClause(it) }
}
