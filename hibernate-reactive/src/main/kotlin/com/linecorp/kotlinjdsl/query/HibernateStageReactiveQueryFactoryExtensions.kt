package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.deleteQuery
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.updateQuery

suspend inline fun <reified T> HibernateStageReactiveQueryFactory.singleQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.singleQuery(dsl) }

suspend inline fun <reified T> HibernateStageReactiveQueryFactory.listQuery(
    noinline dsl: CriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.listQuery(dsl) }

suspend inline fun <reified T: Any> HibernateStageReactiveQueryFactory.updateQuery(
    noinline dsl: CriteriaUpdateQueryDsl.() -> Unit
) = withFactory { it.updateQuery<T>(dsl).executeUpdate }

suspend inline fun <reified T: Any> HibernateStageReactiveQueryFactory.deleteQuery(
    noinline dsl: CriteriaDeleteQueryDsl.() -> Unit
) = withFactory { it.deleteQuery<T>(dsl).executeUpdate }

inline fun <reified T> HibernateStageReactiveQueryFactory.subquery(
    noinline dsl: SubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)

