package com.linecorp.kotlinjdsl.executor.spring.jpa.javax

import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.jpql.JpqlEntityManagerUtils
import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.sql.SqlEntityManagerUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager
import kotlin.reflect.KClass

fun EntityManager.createQuery(
    query: JpqlQuery<*>,
    context: RenderContext
) = JpqlEntityManagerUtils.createQuery(this, query, context)

inline fun <reified T : Any> EntityManager.createQuery(
    query: JpqlQuery<*>,
    context: RenderContext,
) = JpqlEntityManagerUtils.createQuery(this, query, context, T::class)

fun <T : Any> EntityManager.createQuery(
    query: JpqlQuery<*>,
    context: RenderContext,
    resultClass: KClass<T>,
) = JpqlEntityManagerUtils.createQuery(this, query, context, resultClass)

inline fun <reified T : Any> EntityManager.queryForPage(
    query: JpqlQuery<SelectQuery<Any>>,
    context: RenderContext,
    pageable: Pageable,
) = JpqlEntityManagerUtils.queryForPage(this, query, context, pageable, T::class)

fun <T : Any> EntityManager.queryForPage(
    query: JpqlQuery<SelectQuery<Any>>,
    context: RenderContext,
    pageable: Pageable,
    resultClass: KClass<T>,
) = JpqlEntityManagerUtils.queryForPage(this, query, context, pageable, resultClass)

fun EntityManager.createNativeQuery(
    query: SqlQuery<*>,
    context: RenderContext,
) = SqlEntityManagerUtils.createNativeQuery(this, query, context)

fun <T : Any> EntityManager.createNativeQuery(
    query: SqlQuery<*>,
    context: RenderContext,
    resultClass: KClass<T>,
) = SqlEntityManagerUtils.createNativeQuery(this, query, context, resultClass)

