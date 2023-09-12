package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlStageSessionUtils
import org.hibernate.reactive.stage.Stage

/**
 * Creates [Stage.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Stage.Session.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
): Stage.Query<T> = JpqlStageSessionUtils.createQuery(this, query, context)

/**
 * Creates [Stage.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Stage.Session.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Stage.Query<T> = JpqlStageSessionUtils.createQuery(this, query, queryParams, context)

/**
 * Creates [Stage.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Stage.Session.createQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
): Stage.Query<T> = JpqlStageSessionUtils.createQuery(this, query, context)

/**
 * Creates [Stage.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Stage.Session.createQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Stage.Query<T> = JpqlStageSessionUtils.createQuery(this, query, queryParams, context)

/**
 * Creates [Stage.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Stage.Session.createQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
): Stage.Query<T> = JpqlStageSessionUtils.createQuery(this, query, context)

/**
 * Creates [Stage.Query] from the [SelectQuery] and [RenderContext].
 */
@SinceJdsl("3.0.0")
fun <T : Any> Stage.Session.createQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Stage.Query<T> = JpqlStageSessionUtils.createQuery(this, query, queryParams, context)
