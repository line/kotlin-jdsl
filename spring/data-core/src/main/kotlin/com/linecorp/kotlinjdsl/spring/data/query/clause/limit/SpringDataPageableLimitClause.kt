package com.linecorp.kotlinjdsl.spring.data.query.clause.limit

import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import org.springframework.data.domain.Pageable
import javax.persistence.Query

data class SpringDataPageableLimitClause(
    val pageable: Pageable
) : QueryLimitClause<Query> {
    override fun apply(query: Query) {
        if (pageable.isPaged) {
            query.firstResult = pageable.offset.toInt()
            query.maxResults = pageable.pageSize
        }
    }
}
