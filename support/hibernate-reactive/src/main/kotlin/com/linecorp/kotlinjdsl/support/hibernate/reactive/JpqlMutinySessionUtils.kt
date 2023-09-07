package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import kotlin.reflect.KClass
import org.hibernate.reactive.mutiny.Mutiny

internal object JpqlMutinySessionUtils {
    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: SelectQuery<T>,
        context: RenderContext,
    ): Mutiny.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createMutationQuery(
        session: Mutiny.Session,
        query: UpdateQuery<T>,
        context: RenderContext,
    ): Mutiny.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createMutationQuery(session, rendered)
    }

    fun <T : Any> createMutationQuery(
        session: Mutiny.Session,
        query: UpdateQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createMutationQuery(session, rendered)
    }

    fun <T : Any> createMutationQuery(
        session: Mutiny.Session,
        query: DeleteQuery<T>,
        context: RenderContext,
    ): Mutiny.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createMutationQuery(session, rendered)
    }

    fun <T : Any> createMutationQuery(
        session: Mutiny.Session,
        query: DeleteQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.MutationQuery {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createMutationQuery(session, rendered)
    }

    private fun <T : Any> createQuery(
        session: Mutiny.Session,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): Mutiny.SelectionQuery<T> {
        return session.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun createMutationQuery(
        session: Mutiny.Session,
        rendered: JpqlRendered,
    ): Mutiny.MutationQuery {
        return session.createMutationQuery(rendered.query).apply {
            setParams(this, rendered.params)
        }
    }

    private fun <T> setParams(query: Mutiny.SelectionQuery<T>, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }

    private fun setParams(query: Mutiny.MutationQuery, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
