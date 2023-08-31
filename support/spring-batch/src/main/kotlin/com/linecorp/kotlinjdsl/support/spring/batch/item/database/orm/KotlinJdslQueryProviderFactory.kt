package com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext

class KotlinJdslQueryProviderFactory(
    private val context: RenderContext,
) {
    fun <T : Any> createKotlinJdslQueryProvider(
        query: SelectQuery<T>,
    ): KotlinJdslQueryProvider<T> {
        return KotlinJdslQueryProvider(query, emptyMap(), context)
    }

    fun <T : Any> createKotlinJdslQueryProvider(
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
    ): KotlinJdslQueryProvider<T> {
        return KotlinJdslQueryProvider(query, queryParams, context)
    }
}
