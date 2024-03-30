package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import org.hibernate.reactive.mutiny.Mutiny
import kotlin.reflect.KClass

internal object JpqlMutinySessionUtils {
    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered.query, rendered.params)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered.query, rendered.params)
    }

    private fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): Mutiny.Query<T> {
        return session.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }
    }

    private fun <T> createQuery(
        session: Mutiny.Session,
        query: String,
        queryParams: Map<String, Any?>,
    ): Mutiny.Query<T> {
        return session.createQuery<T>(query).apply {
            setParams(this, queryParams)
        }
    }

    private fun <T> setParams(query: Mutiny.Query<T>, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
