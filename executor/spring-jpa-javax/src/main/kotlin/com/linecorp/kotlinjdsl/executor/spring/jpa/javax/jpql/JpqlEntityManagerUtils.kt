@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.executor.spring.jpa.javax.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.query.QueryUtilsAdaptor
import org.springframework.data.support.PageableExecutionUtils
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

object JpqlEntityManagerUtils {
    fun createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        val renderedQuery = rendered.query
        val renderedParams = rendered.params

        return entityManager.createQuery(renderedQuery).apply {
            setParams(this, renderedParams)
        }
    }

    inline fun <reified T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): TypedQuery<T> {
        return createQuery(entityManager, query, context, T::class)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        context: RenderContext,
        resultClass: KClass<T>,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        val renderedQuery = rendered.query
        val renderedParams = rendered.params

        return entityManager.createQuery(renderedQuery, resultClass.java).apply {
            setParams(this, renderedParams)
        }
    }

    fun <T : Any> queryForPage(
        entityManager: EntityManager,
        query: JpqlQuery<SelectQuery<Any>>,
        context: RenderContext,
        pageable: Pageable,
        resultClass: KClass<T>,
    ): Page<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        val renderedQuery = rendered.query
        val renderedParams = rendered.params

        val sortedQuery = QueryUtilsAdaptor.applySorting(renderedQuery, pageable.sort)
        val countQuery = QueryUtilsAdaptor.createCountQueryFor(renderedQuery, null, false)

        val sortedJpaQuery = entityManager.createQuery(sortedQuery, resultClass.java).apply {
            setParams(this, renderedParams)
        }

        val countJpaQuery = entityManager.createQuery(countQuery, Long::class.java).apply {
            setParams(this, renderedParams)
        }

        return PageableExecutionUtils.getPage(sortedJpaQuery.resultList, pageable) {
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
