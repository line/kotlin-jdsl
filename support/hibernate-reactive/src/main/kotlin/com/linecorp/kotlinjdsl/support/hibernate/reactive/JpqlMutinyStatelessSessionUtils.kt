package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import kotlin.reflect.KClass
import org.hibernate.reactive.mutiny.Mutiny

internal object JpqlMutinyStatelessSessionUtils {
    fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        query: SelectQuery<T>,
        context: RenderContext,
    ): Mutiny.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.SelectionQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        query: UpdateQuery<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered)
    }

    fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        query: UpdateQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered)
    }

    fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        query: DeleteQuery<T>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(session, rendered)
    }

    fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        query: DeleteQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Mutiny.Query<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(session, rendered)
    }

    private fun <T : Any> createQuery(
        session: Mutiny.StatelessSession,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): Mutiny.SelectionQuery<T> {
        return session.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun <T> createQuery(
        session: Mutiny.StatelessSession,
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

    private fun <T> setParams(query: Mutiny.SelectionQuery<T>, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
