package com.linecorp.kotlinjdsl.support.spring.batch.javax.item.database.orm

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.batch.javax.JpqlEntityManagerUtils
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider
import javax.persistence.Query

/**
 * [AbstractJpaQueryProvider] implementation for KotlinJdsl.
 */
class KotlinJdslQueryProvider<T : Any>(
    private val query: SelectQuery<T>,
    private val queryParams: Map<String, Any?>,
    private val context: RenderContext,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        return JpqlEntityManagerUtils.createQuery(entityManager, query, queryParams, query.returnType, context)
    }

    override fun afterPropertiesSet() {
        // ignore
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotlinJdslQueryProvider<*>

        if (query != other.query) return false
        if (queryParams != other.queryParams) return false
        return context == other.context
    }

    override fun hashCode(): Int {
        var result = query.hashCode()
        result = 31 * result + queryParams.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }
}
