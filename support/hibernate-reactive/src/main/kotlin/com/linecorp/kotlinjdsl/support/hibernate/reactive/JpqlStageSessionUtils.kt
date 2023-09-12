package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import org.hibernate.reactive.stage.Stage
import kotlin.reflect.KClass

internal object JpqlStageSessionUtils {
    fun <T : Any> createQuery(
        session: Stage.Session,
        query: SelectQuery<T>,
        context: RenderContext,
    ): Stage.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        session: Stage.Session,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Stage.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createMutationQuery(
        session: Stage.Session,
        query: UpdateQuery<T>,
        context: RenderContext,
    ): Stage.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createMutationQuery(session, rendered)
    }

    fun <T : Any> createMutationQuery(
        session: Stage.Session,
        query: UpdateQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Stage.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createMutationQuery(session, rendered)
    }

    fun <T : Any> createMutationQuery(
        session: Stage.Session,
        query: DeleteQuery<T>,
        context: RenderContext,
    ): Stage.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createMutationQuery(session, rendered)
    }

    fun <T : Any> createMutationQuery(
        session: Stage.Session,
        query: DeleteQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Stage.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createMutationQuery(session, rendered)
    }

    private fun <T : Any> createQuery(
        session: Stage.Session,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): Stage.SelectionQuery<T> {
        return session.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun createMutationQuery(
        session: Stage.Session,
        rendered: JpqlRendered,
    ): Stage.MutationQuery {
        return session.createMutationQuery(rendered.query).apply {
            setParams(this, rendered.params)
        }
    }

    private fun <T> setParams(query: Stage.SelectionQuery<T>, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }

    private fun setParams(query: Stage.MutationQuery, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
