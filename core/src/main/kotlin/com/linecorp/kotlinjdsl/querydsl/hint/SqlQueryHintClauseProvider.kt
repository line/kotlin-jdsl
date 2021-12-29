package com.linecorp.kotlinjdsl.querydsl.hint

import com.linecorp.kotlinjdsl.query.clause.hint.EmptySqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause

object SqlQueryHintClauseProvider {
    val provide: (List<String>) -> SqlQueryHintClause

    init {
        provide = if (isPresent("org.hibernate.query.Query")) {
            { hints -> HibernateSqlQueryHintClause(hints) }
        } else {
            { EmptySqlQueryHintClause }
        }
    }

    @Suppress("SameParameterValue")
    private fun isPresent(className: String) = try {
        Class.forName(className)
        true
    } catch (e: Throwable) {
        false
    }
}