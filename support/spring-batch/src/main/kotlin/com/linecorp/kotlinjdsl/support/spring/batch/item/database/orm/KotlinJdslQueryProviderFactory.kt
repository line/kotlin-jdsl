package com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext

class KotlinJdslQueryProviderFactory(
    private val context: RenderContext,
) {
    fun <T : Any> create(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): KotlinJdslQueryProvider<T> {
        val query = jpql(init)

        return KotlinJdslQueryProvider(query, emptyMap(), context)
    }

    fun <T : Any> create(
        queryParams: Map<String, Any?>,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): KotlinJdslQueryProvider<T> {
        val query = jpql(init)

        return KotlinJdslQueryProvider(query, queryParams, context)
    }

    fun <DSL : JpqlDsl, T : Any> create(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): KotlinJdslQueryProvider<T> {
        val query = jpql(dsl, init)

        return KotlinJdslQueryProvider(query, emptyMap(), context)
    }

    fun <DSL : JpqlDsl, T : Any> create(
        dsl: JpqlDsl.Constructor<DSL>,
        queryParams: Map<String, Any?>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): KotlinJdslQueryProvider<T> {
        val query = jpql(dsl, init)

        return KotlinJdslQueryProvider(query, queryParams, context)
    }

    fun <T : Any> create(
        query: SelectQuery<T>,
    ): KotlinJdslQueryProvider<T> {
        return KotlinJdslQueryProvider(query, emptyMap(), context)
    }

    fun <T : Any> create(
        queryParams: Map<String, Any?>,
        query: SelectQuery<T>,
    ): KotlinJdslQueryProvider<T> {
        return KotlinJdslQueryProvider(query, queryParams, context)
    }
}
