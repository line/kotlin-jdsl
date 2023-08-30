package com.linecorp.kotlinjdsl.support.eclipse.javax.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.eclipse.javax.JpqlJpaEntityManagerUtils
import org.eclipse.persistence.jpa.JpaEntityManager

fun <T : Any> JpaEntityManager.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
) = JpqlJpaEntityManagerUtils.createQuery(this, query, context)

fun <T : Any> JpaEntityManager.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
) = JpqlJpaEntityManagerUtils.createQuery(this, query, queryParams, context)

fun <T : Any> JpaEntityManager.createQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
) = JpqlJpaEntityManagerUtils.createQuery(this, query, context)

fun <T : Any> JpaEntityManager.createQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
) = JpqlJpaEntityManagerUtils.createQuery(this, query, queryParams, context)

fun <T : Any> JpaEntityManager.createQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
) = JpqlJpaEntityManagerUtils.createQuery(this, query, context)

fun <T : Any> JpaEntityManager.createQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
) = JpqlJpaEntityManagerUtils.createQuery(this, query, queryParams, context)
