@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.executor.spring.jpa.javax.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.query.QueryUtilsAdaptor
import org.springframework.data.support.PageableExecutionUtilsAdaptor
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

object JpqlEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: UpdateQuery<T>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: UpdateQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: DeleteQuery<T>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: DeleteQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered)
    }

    private fun createQuery(
        entityManager: EntityManager,
        rendered: JpqlRendered,
    ): Query {
        return entityManager.createQuery(rendered.query).apply {
            setParams(this, rendered.params)
        }
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

    fun <T : Any> queryForPage(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        pageable: Pageable,
        context: RenderContext,
    ): Page<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return queryForPage(entityManager, rendered, pageable, query.returnType)
    }

    fun <T : Any> queryForPage(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        pageable: Pageable,
        context: RenderContext,
    ): Page<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return queryForPage(entityManager, rendered, pageable, query.returnType)
    }

    private fun <T : Any> queryForPage(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        pageable: Pageable,
        resultClass: KClass<T>,
    ): Page<T> {
        val sortedQuery = QueryUtilsAdaptor.applySorting(rendered.query, pageable.sort)
        val countQuery = QueryUtilsAdaptor.createCountQueryFor(rendered.query, null, false)

        val sortedJpaQuery = entityManager.createQuery(sortedQuery, resultClass.java).apply {
            setParams(this, rendered.params)

            if (pageable.isPaged) {
                firstResult = pageable.offset.toInt()
                maxResults = pageable.pageSize
            }
        }

        val countJpaQuery = entityManager.createQuery(countQuery, Long::class.javaObjectType).apply {
            setParams(this, rendered.params)
        }

        return PageableExecutionUtilsAdaptor.getPage(sortedJpaQuery.resultList, pageable) {
            val counts = countJpaQuery.resultList

            if (counts.size == 1) {
                counts.first()
            } else {
                counts.count().toLong()
            }
        }
    }

    private fun setParams(query: Query, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
