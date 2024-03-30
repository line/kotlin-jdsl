package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import org.hibernate.reactive.stage.Stage
import kotlin.reflect.KClass

internal object JpqlStageSessionUtils {
    fun <T : Any> createQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Stage.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Stage.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Stage.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered.query, rendered.params)
    }

    fun <T : Any> createQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Stage.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered.query, rendered.params)
    }

    private fun <T : Any> createQuery(
        session: Stage.Session,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): Stage.Query<T> {
        return session.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }
    }

    private fun <T> createQuery(
        session: Stage.Session,
        query: String,
        queryParams: Map<String, Any?>,
    ): Stage.Query<T> {
        return session.createQuery<T>(query).apply {
            setParams(this, queryParams)
        }
    }

    private fun <T> setParams(query: Stage.Query<T>, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
