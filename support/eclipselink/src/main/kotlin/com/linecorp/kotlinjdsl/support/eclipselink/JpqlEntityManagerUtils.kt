package com.linecorp.kotlinjdsl.support.eclipselink

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kotlin.reflect.KClass

internal object JpqlEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered.query, rendered.params, returnType.java)
    }

    fun createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered.query, rendered.params)
    }

    fun createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered.query, rendered.params)
    }

    private fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): TypedQuery<T> {
        return entityManager.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }
    }

    private fun createQuery(
        entityManager: EntityManager,
        query: String,
        queryParams: Map<String, Any?>,
    ): Query {
        return entityManager.createQuery(query).apply {
            setParams(this, queryParams)
        }
    }

    private fun setParams(query: Query, params: Map<String, Any?>) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
