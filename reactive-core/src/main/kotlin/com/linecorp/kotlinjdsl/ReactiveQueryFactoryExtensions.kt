package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl

inline fun <reified T> ReactiveQueryFactory.singleQuery(noinline dsl: CriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(dsl).singleResult

inline fun <reified T> ReactiveQueryFactory.singleQueryOrNull(noinline dsl: CriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(dsl).singleResultOrNull

inline fun <reified T> ReactiveQueryFactory.listQuery(noinline dsl: CriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(dsl).resultList

inline fun <reified T> ReactiveQueryFactory.selectQuery(noinline dsl: CriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(T::class.java, dsl)

inline fun <reified T : Any> ReactiveQueryFactory.updateQuery(noinline dsl: CriteriaUpdateQueryDsl.() -> Unit) =
    updateQuery(T::class, dsl)

inline fun <reified T : Any> ReactiveQueryFactory.deleteQuery(noinline dsl: CriteriaDeleteQueryDsl.() -> Unit) =
    deleteQuery(T::class, dsl)

inline fun <reified T> ReactiveQueryFactory.subquery(noinline dsl: SubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)
