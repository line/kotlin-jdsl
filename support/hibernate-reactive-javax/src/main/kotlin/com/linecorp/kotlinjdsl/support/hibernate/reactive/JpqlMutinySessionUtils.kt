package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import org.hibernate.reactive.mutiny.Mutiny
import kotlin.reflect.KClass

internal object JpqlMutinySessionUtils {
    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: SelectQuery<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: UpdateQuery<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: UpdateQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: DeleteQuery<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered)
    }

    fun <T : Any> createQuery(
        session: Mutiny.Session,
        query: DeleteQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered)
    }

    private fun <T : Any> createQuery(
        session: Mutiny.Session,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): Mutiny.Query<T> {
        return session.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun <T> createQuery(
        session: Mutiny.Session,
        rendered: JpqlRendered,
    ): Mutiny.Query<T> {
        return session.createQuery<T>(rendered.query).apply {
            setParams(this, rendered.params)
        }
    }

    private fun <T> setParams(query: Mutiny.Query<T>, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
