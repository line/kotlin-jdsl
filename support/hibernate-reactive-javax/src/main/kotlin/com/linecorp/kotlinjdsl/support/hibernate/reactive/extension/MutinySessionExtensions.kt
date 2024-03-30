package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlMutinySessionUtils
import org.hibernate.reactive.mutiny.Mutiny

/**
 * Creates a [Mutiny.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
): Mutiny.Query<T> = JpqlMutinySessionUtils.createQuery(this, query, query.returnType, context)

/**
 * Creates a [Mutiny.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Mutiny.Query<T> = JpqlMutinySessionUtils.createQuery(this, query, queryParams, query.returnType, context)

/**
 * Creates a [Mutiny.Query] from the [UpdateQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
): Mutiny.Query<T> = JpqlMutinySessionUtils.createQuery(this, query, context)

/**
 * Creates a [Mutiny.Query] from the [UpdateQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Mutiny.Query<T> = JpqlMutinySessionUtils.createQuery(this, query, queryParams, context)

/**
 * Creates a [Mutiny.Query] from the [DeleteQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
): Mutiny.Query<T> = JpqlMutinySessionUtils.createQuery(this, query, context)

/**
 * Creates a [Mutiny.Query] from the [DeleteQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Mutiny.Query<T> = JpqlMutinySessionUtils.createQuery(this, query, queryParams, context)
