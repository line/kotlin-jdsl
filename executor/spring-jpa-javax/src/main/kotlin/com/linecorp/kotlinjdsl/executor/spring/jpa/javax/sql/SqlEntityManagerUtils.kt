@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.executor.spring.jpa.javax.sql

import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import javax.persistence.EntityManager
import javax.persistence.Query
import kotlin.reflect.KClass

object SqlEntityManagerUtils {
    fun createNativeQuery(
        entityManager: EntityManager,
        query: SqlQuery<*>,
        context: RenderContext,
    ): Query {
        val rendered = SqlRendererHolder.get().render(query, context)

        val renderedQuery = rendered.query
        val renderedParams = rendered.params

        val jpaQuery = entityManager.createNativeQuery(renderedQuery)

        when (renderedParams) {
            is SqlRenderedParams.Indexed -> setParams(jpaQuery, renderedParams)
            is SqlRenderedParams.Named -> setParams(jpaQuery, renderedParams)
        }

        return jpaQuery
    }

    fun <T : Any> createNativeQuery(
        entityManager: EntityManager,
        query: SqlQuery<*>,
        context: RenderContext,
        resultClass: KClass<T>,
    ): Query {
        val rendered = SqlRendererHolder.get().render(query, context)

        val renderedQuery = rendered.query
        val renderedParams = rendered.params

        val jpaQuery = entityManager.createNativeQuery(renderedQuery, resultClass.java)

        when (renderedParams) {
            is SqlRenderedParams.Indexed -> setParams(jpaQuery, renderedParams)
            is SqlRenderedParams.Named -> setParams(jpaQuery, renderedParams)
        }

        return jpaQuery
    }

    private fun setParams(query: Query, params: SqlRenderedParams.Indexed) {
        params.forEachIndexed { index, value ->
            query.setParameter(index, value)
        }
    }

    private fun setParams(query: Query, params: SqlRenderedParams.Named) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
