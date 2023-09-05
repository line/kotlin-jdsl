package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlMutinySessionUtils
import org.hibernate.reactive.mutiny.Mutiny

@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
): Mutiny.SelectionQuery<T> = JpqlMutinySessionUtils.createQuery(this, query, context)

@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Mutiny.SelectionQuery<T> = JpqlMutinySessionUtils.createQuery(this, query, queryParams, context)

@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createMutationQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
): Mutiny.MutationQuery = JpqlMutinySessionUtils.createMutationQuery(this, query, context)

@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createMutationQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Mutiny.MutationQuery = JpqlMutinySessionUtils.createMutationQuery(this, query, queryParams, context)

@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createMutationQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
): Mutiny.MutationQuery = JpqlMutinySessionUtils.createMutationQuery(this, query, context)

@SinceJdsl("3.0.0")
fun <T : Any> Mutiny.Session.createMutationQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
): Mutiny.MutationQuery = JpqlMutinySessionUtils.createMutationQuery(this, query, queryParams, context)
