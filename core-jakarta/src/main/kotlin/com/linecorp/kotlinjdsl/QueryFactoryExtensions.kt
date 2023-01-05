package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import java.util.stream.Stream

inline fun <reified T> QueryFactory.singleQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
): T = selectQuery(T::class.java, dsl).singleResult

inline fun <reified T> QueryFactory.listQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
): List<T> = selectQuery(T::class.java, dsl).resultList

inline fun <reified T> QueryFactory.streamQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
): Stream<T> = selectQuery(T::class.java, dsl).resultStream


inline fun <reified T> QueryFactory.selectQuery(noinline dsl: CriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(T::class.java, dsl)

inline fun <reified T : Any> QueryFactory.updateQuery(noinline dsl: CriteriaUpdateQueryDsl.() -> Unit) =
    updateQuery(T::class, dsl)

inline fun <reified T : Any> QueryFactory.deleteQuery(noinline dsl: CriteriaDeleteQueryDsl.() -> Unit) =
    deleteQuery(T::class, dsl)

inline fun <reified T> QueryFactory.subquery(noinline dsl: SubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)
