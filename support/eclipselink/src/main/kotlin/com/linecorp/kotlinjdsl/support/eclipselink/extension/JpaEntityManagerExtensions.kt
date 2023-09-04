package com.linecorp.kotlinjdsl.support.eclipselink.extension

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.eclipselink.JpqlJpaEntityManagerUtils
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.eclipse.persistence.jpa.JpaEntityManager

/**
 * Creates [jakarta.persistence.TypedQuery] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> JpaEntityManager.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
): TypedQuery<T> = JpqlJpaEntityManagerUtils.createQuery(this, query, context)

/**
 * Creates [jakarta.persistence.TypedQuery] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> JpaEntityManager.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): TypedQuery<T> = JpqlJpaEntityManagerUtils.createQuery(this, query, queryParams, context)

/**
 * Creates [jakarta.persistence.Query] from the [UpdateQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> JpaEntityManager.createQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
): Query = JpqlJpaEntityManagerUtils.createQuery(this, query, context)

/**
 * Creates [jakarta.persistence.Query] from the [UpdateQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> JpaEntityManager.createQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Query = JpqlJpaEntityManagerUtils.createQuery(this, query, queryParams, context)

/**
 * Creates [jakarta.persistence.Query] from the [DeleteQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> JpaEntityManager.createQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
): Query = JpqlJpaEntityManagerUtils.createQuery(this, query, context)

/**
 * Creates [jakarta.persistence.Query] from the [DeleteQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> JpaEntityManager.createQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Query = JpqlJpaEntityManagerUtils.createQuery(this, query, queryParams, context)
