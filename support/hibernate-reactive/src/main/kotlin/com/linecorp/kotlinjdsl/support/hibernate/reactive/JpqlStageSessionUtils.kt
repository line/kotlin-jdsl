package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import org.hibernate.reactive.stage.Stage
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

internal object JpqlStageSessionUtils {
    fun <T : Any> createQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Stage.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): Stage.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered.query, rendered.params, returnType.java)
    }

    fun createMutationQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Stage.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createMutationQuery(session, rendered.query, rendered.params)
    }

    fun createMutationQuery(
        session: Stage.Session,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Stage.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createMutationQuery(session, rendered.query, rendered.params)
    }

    private fun <T : Any> createQuery(
        session: Stage.Session,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): Stage.SelectionQuery<T> {
        return session.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }
    }

    private fun createMutationQuery(
        session: Stage.Session,
        query: String,
        queryParams: Map<String, Any?>,
    ): Stage.MutationQuery {
        return session.createMutationQuery(query).apply {
            setParams(this, queryParams)
        }
    }

    private fun <T> setParams(query: Stage.SelectionQuery<T>, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            try {
                query.setParameter(name, value)
            } catch (e: RuntimeException) {
                if (log.isDebugEnabled) {
                    log.debug("Silently ignoring", e)
                }
            }
        }
    }

    private fun setParams(query: Stage.MutationQuery, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            try {
                query.setParameter(name, value)
            } catch (e: RuntimeException) {
                if (log.isDebugEnabled) {
                    log.debug("Silently ignoring", e)
                }
            }
        }
    }
}

private val log = LoggerFactory.getLogger(JpqlStageSessionUtils::class.java)
