package com.linecorp.kotlinjdsl.support.eclipselink.javax.extension

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.eclipselink.javax.JpqlEntityManagerUtils
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery

/**
 * Creates a [javax.persistence.TypedQuery] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> EntityManager.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
): TypedQuery<T> = JpqlEntityManagerUtils.createQuery(this, query, query.returnType, context)

/**
 * Creates a [javax.persistence.TypedQuery] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> EntityManager.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): TypedQuery<T> = JpqlEntityManagerUtils.createQuery(this, query, queryParams, query.returnType, context)

/**
 * Creates a [javax.persistence.Query] from the [UpdateQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> EntityManager.createQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
): Query = JpqlEntityManagerUtils.createQuery(this, query, context)

/**
 * Creates a [javax.persistence.Query] from the [UpdateQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> EntityManager.createQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Query = JpqlEntityManagerUtils.createQuery(this, query, queryParams, context)

/**
 * Creates a [javax.persistence.Query] from the [DeleteQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> EntityManager.createQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
): Query = JpqlEntityManagerUtils.createQuery(this, query, context)

/**
 * Creates a [javax.persistence.Query] from the [DeleteQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> EntityManager.createQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Query = JpqlEntityManagerUtils.createQuery(this, query, queryParams, context)
