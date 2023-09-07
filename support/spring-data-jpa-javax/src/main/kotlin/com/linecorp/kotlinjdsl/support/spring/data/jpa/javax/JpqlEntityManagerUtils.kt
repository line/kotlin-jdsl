@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.jpa.repository.query.QueryUtilsAdaptor
import org.springframework.data.support.PageableExecutionUtilsAdaptor

internal object JpqlEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): TypedQuery<T> {
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

    private fun <T : Any> createQuery(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): TypedQuery<T> {
        return entityManager.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun createQuery(
        entityManager: EntityManager,
        rendered: JpqlRendered,
    ): Query {
        return entityManager.createQuery(rendered.query).apply {
            setParams(this, rendered.params)
        }
    }

    fun <T : Any> queryForList(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        pageable: Pageable,
        context: RenderContext,
    ): List<T?> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return queryForList(entityManager, rendered, pageable, query.returnType)
    }

    fun <T : Any> queryForPage(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        pageable: Pageable,
        context: RenderContext,
    ): Page<T?> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return queryForPage(entityManager, rendered, pageable, query.returnType)
    }

    fun <T : Any> queryForSlice(
        entityManager: EntityManager,
        query: SelectQuery<T>,
        pageable: Pageable,
        context: RenderContext,
    ): Slice<T?> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return queryForSlice(entityManager, rendered, pageable, query.returnType)
    }

    private fun <T : Any> queryForList(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        pageable: Pageable,
        resultClass: KClass<T>,
    ): List<T?> {
        val sortedQuery = QueryUtilsAdaptor.applySorting(rendered.query, pageable.sort)

        val jpaQuery = entityManager.createQuery(sortedQuery, resultClass.java).apply {
            setParams(this, rendered.params)
        }

        if (pageable.isPaged) {
            jpaQuery.firstResult = pageable.offset.toInt()
            jpaQuery.maxResults = pageable.pageSize
        }

        return jpaQuery.resultList
    }

    private fun <T : Any> queryForPage(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        pageable: Pageable,
        resultClass: KClass<T>,
    ): Page<T?> {
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

    private fun <T : Any> queryForSlice(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        pageable: Pageable,
        resultClass: KClass<T>,
    ): Slice<T?> {
        val sortedQuery = QueryUtilsAdaptor.applySorting(rendered.query, pageable.sort)

        val jpaQuery = entityManager.createQuery(sortedQuery, resultClass.java).apply {
            setParams(this, rendered.params)
        }

        return if (pageable.isPaged) {
            jpaQuery.firstResult = pageable.offset.toInt()
            jpaQuery.maxResults = pageable.pageSize + 1

            val results = jpaQuery.resultList
            val hasNext = results.size > pageable.pageSize

            SliceImpl(takeIf { hasNext }?.let { results.dropLast(1) } ?: results, pageable, hasNext)
        } else {
            val results = jpaQuery.resultList

            SliceImpl(results, pageable, false)
        }
    }

    private fun setParams(query: Query, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
