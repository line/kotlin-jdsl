package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl

inline fun <reified T> QueryFactory.singleQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
): T = typedQuery(T::class.java, dsl).singleResult

inline fun <reified T> QueryFactory.listQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
): List<T> = typedQuery(T::class.java, dsl).resultList

inline fun <reified T> QueryFactory.typedQuery(noinline dsl: CriteriaQueryDsl<T>.() -> Unit) =
    typedQuery(T::class.java, dsl)

inline fun <reified T> QueryFactory.subquery(noinline dsl: SubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)