@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.support.spring.data.jpa

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.query.QueryEnhancerFactoryAdaptor
import org.springframework.data.jpa.repository.query.QueryUtils
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

    /**
     * Creates a [TypedQuery] for a count query with boxed [java.lang.Long] as the return type.
     *
     * This is separated from the general [createQuery] to work around a Hibernate 5 strictness issue:
     * the primitive `long` vs. boxed `java.lang.Long` mismatch is rejected with
     * `Type specified for TypedQuery [long] is incompatible with query return type [class java.lang.Long]`.
     *
     * Hibernate 6 is lenient about this mismatch today, but the same latent issue remains.
     * Applied consistently across all three support modules (spring-data-jpa, spring-data-jpa-javax,
     * spring-data-jpa-boot4) to remain safe when future Hibernate versions tighten type inference.
     *
     * This matches the convention already used by the auto-count path in `createEnhancedQuery`.
     */
    fun createCountQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): TypedQuery<Long> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered.query, rendered.params, Long::class.javaObjectType)
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

    fun <T : Any> createEnhancedQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        sort: Sort,
        context: RenderContext,
    ): EnhancedTypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        val sortedQueryString = QueryUtils.applySorting(rendered.query, sort)

        return EnhancedTypedQuery(
            createQuery(entityManager, sortedQueryString, rendered.params, returnType.java),
        ) {
            // Use JDSL model to create the count query instead of relying on string parsing
            if (query is SelectQuery<*>) {
                val countQuery = SelectQueries.toCountQuery(query)

                val renderedCount = JpqlRendererHolder.get().render(countQuery, rendered.params, context)

                createQuery(
                    entityManager,
                    renderedCount.query,
                    renderedCount.params,
                    Long::class.javaObjectType,
                )
            } else {
                // Fallback for non-select queries using Spring's enhancer
                val queryEnhancer = QueryEnhancerFactoryAdaptor.forQuery(rendered.query)

                createQuery(
                    entityManager,
                    queryEnhancer.createCountQueryFor(null),
                    rendered.params,
                    Long::class.javaObjectType,
                )
            }
        }
    }

    private fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): TypedQuery<T> =
        entityManager.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }

    private fun createQuery(
        entityManager: EntityManager,
        query: String,
        queryParams: Map<String, Any?>,
    ): Query =
        entityManager.createQuery(query).apply {
            setParams(this, queryParams)
        }

    private fun setParams(
        query: Query,
        params: Map<String, Any?>,
    ) {
        val parameterNameSet = query.parameters.map { it.name }.toHashSet()

        params.forEach { (name, value) ->
            if (parameterNameSet.contains(name)) {
                query.setParameter(name, value)
            } else {
                if (log.isDebugEnabled) {
                    log.debug(
                        "No parameter named '$name' in query " +
                            "with named parameters [${parameterNameSet.joinToString()}], " +
                            "parameter binding skipped",
                    )
                }
            }
        }
    }
}

private val log = LoggerFactory.getLogger(JpqlEntityManagerUtils::class.java)
