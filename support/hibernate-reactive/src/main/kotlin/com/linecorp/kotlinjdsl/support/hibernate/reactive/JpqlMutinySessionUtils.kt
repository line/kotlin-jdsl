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
    ): Mutiny.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Mutiny.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun createMutationQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Mutiny.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createMutationQuery(session, rendered.query, rendered.params)
    }

    fun createMutationQuery(
        session: Mutiny.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createMutationQuery(session, rendered.query, rendered.params)
    }

    private fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): Mutiny.SelectionQuery<T> {
        return session.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }
    }

    private fun createMutationQuery(
        session: Mutiny.Session,
        query: String,
        queryParams: Map<String, Any?>,
    ): Mutiny.MutationQuery {
        return session.createMutationQuery(query).apply {
            setParams(this, queryParams)
        }
    }

    private fun <T> setParams(query: Mutiny.SelectionQuery<T>, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }

    private fun setParams(query: Mutiny.MutationQuery, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
