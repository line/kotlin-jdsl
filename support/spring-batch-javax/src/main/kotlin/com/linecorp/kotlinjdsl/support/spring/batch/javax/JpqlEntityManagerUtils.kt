package com.linecorp.kotlinjdsl.support.spring.batch.javax

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

internal object JpqlEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered, query.returnType)
    }

    private fun <T : Any> createQuery(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): TypedQuery<T> {
        return entityManager.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun setParams(query: Query, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
