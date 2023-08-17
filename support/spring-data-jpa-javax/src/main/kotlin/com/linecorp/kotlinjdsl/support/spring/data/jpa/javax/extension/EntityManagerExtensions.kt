package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.JpqlEntityManagerUtils
import javax.persistence.EntityManager

fun <T : Any> EntityManager.createQuery(
    query: SelectQuery<T>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, context)

fun <T : Any> EntityManager.createQuery(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, queryParams, context)

fun <T : Any> EntityManager.createQuery(
    query: UpdateQuery<T>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, context)

fun <T : Any> EntityManager.createQuery(
    query: UpdateQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, queryParams, context)

fun <T : Any> EntityManager.createQuery(
    query: DeleteQuery<T>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, context)

fun <T : Any> EntityManager.createQuery(
    query: DeleteQuery<T>,
    queryParams: Map<String, Any?>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, queryParams, context)
