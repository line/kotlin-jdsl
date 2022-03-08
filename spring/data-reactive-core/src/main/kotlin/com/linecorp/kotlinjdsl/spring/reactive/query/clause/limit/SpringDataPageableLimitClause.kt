package com.linecorp.kotlinjdsl.spring.reactive.query.clause.limit

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import org.springframework.data.domain.Pageable

data class SpringDataPageableLimitClause<Q>(
    val pageable: Pageable
) : QueryLimitClause<ReactiveQuery<Q>> {
    override fun apply(query: ReactiveQuery<Q>) {
        if (pageable.isPaged) {
            query.setFirstResult(pageable.offset.toInt())
            query.setMaxResults(pageable.pageSize)
        }
    }
}
