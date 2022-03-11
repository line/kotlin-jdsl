package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.*
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl

suspend inline fun <reified T> HibernateMutinyReactiveQueryFactory.singleQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.singleQuery(dsl) }

suspend inline fun <reified T> HibernateMutinyReactiveQueryFactory.singleQueryOrNull(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.singleQueryOrNull(dsl) }

suspend inline fun <reified T> HibernateMutinyReactiveQueryFactory.listQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.listQuery(dsl) }

suspend inline fun <reified T : Any> HibernateMutinyReactiveQueryFactory.updateQuery(
    noinline dsl: CriteriaUpdateQueryDsl.() -> Unit
) = withFactory { it.updateQuery<T>(dsl).executeUpdate }

suspend inline fun <reified T : Any> HibernateMutinyReactiveQueryFactory.deleteQuery(
    noinline dsl: CriteriaDeleteQueryDsl.() -> Unit
) = withFactory { it.deleteQuery<T>(dsl).executeUpdate }

inline fun <reified T> HibernateMutinyReactiveQueryFactory.subquery(
    noinline dsl: SubqueryDsl<T>.() -> Unit
) = subquery(T::class.java, dsl)
