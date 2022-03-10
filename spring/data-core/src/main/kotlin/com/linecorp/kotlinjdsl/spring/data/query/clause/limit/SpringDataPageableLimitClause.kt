package com.linecorp.kotlinjdsl.spring.data.query.clause.limit

import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import org.springframework.data.domain.Pageable
import javax.persistence.Query

data class SpringDataPageableLimitClause<Q : Query>(
    val pageable: Pageable
) : QueryLimitClause<Q> {
    override fun apply(query: Q) {
        if (pageable.isPaged) {
            query.firstResult = pageable.offset.toInt()
            query.maxResults = pageable.pageSize
        }
    }
}
