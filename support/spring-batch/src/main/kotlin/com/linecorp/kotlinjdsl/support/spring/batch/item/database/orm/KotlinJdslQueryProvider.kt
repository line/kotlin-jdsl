package com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.batch.JpqlEntityManagerUtils
import jakarta.persistence.Query
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider

class KotlinJdslQueryProvider<T : Any>(
    private val query: SelectQuery<T>,
    private val queryParams: Map<String, Any?>,
    private val context: RenderContext,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        return JpqlEntityManagerUtils.createQuery(entityManager, query, queryParams, context)
    }

    override fun afterPropertiesSet() {
        // ignore
    }
}
