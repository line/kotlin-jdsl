package com.linecorp.kotlinjdsl.query.clause.hint

import com.linecorp.kotlinjdsl.query.ReactiveQuery

data class JpaReactiveQueryHintClauseImpl<T>(
    val hints: Map<String, Any>
) : JpaQueryHintClause<ReactiveQuery<T>> {
    override fun apply(query: ReactiveQuery<T>) {
        hints.forEach { (hintName, value) -> query.setQueryHint(hintName, value) }
    }
}
