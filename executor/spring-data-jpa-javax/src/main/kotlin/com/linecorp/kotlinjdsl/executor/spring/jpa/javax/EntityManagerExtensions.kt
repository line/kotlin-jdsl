package com.linecorp.kotlinjdsl.executor.spring.jpa.javax

import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.jpql.JpqlEntityManagerUtils
import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.sql.SqlEntityManagerUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager
import kotlin.reflect.KClass

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

fun <T : Any> EntityManager.queryForPage(
    query: SelectQuery<T>,
    pageable: Pageable,
    context: RenderContext,
) = JpqlEntityManagerUtils.queryForPage(this, query, pageable, context)

fun <T : Any> EntityManager.queryForPage(
    query: SelectQuery<T>,
    queryParams: Map<String, Any?>,
    pageable: Pageable,
    context: RenderContext,
) = JpqlEntityManagerUtils.queryForPage(this, query, queryParams, pageable, context)

fun EntityManager.createNativeQuery(
    query: SqlQuery<*>,
    context: RenderContext,
) = SqlEntityManagerUtils.createNativeQuery(this, query, context)

fun <T : Any> EntityManager.createNativeQuery(
    query: SqlQuery<*>,
    context: RenderContext,
    resultClass: KClass<T>,
) = SqlEntityManagerUtils.createNativeQuery(this, query, context, resultClass)

