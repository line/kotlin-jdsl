package com.linecorp.kotlinjdsl.spring.reactive

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.*
import org.springframework.data.domain.Pageable

inline fun <reified T> SpringDataReactiveQueryFactory.singleQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
) = selectQuery(dsl).singleResult

inline fun <reified T> SpringDataReactiveQueryFactory.listQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
) = selectQuery(dsl).resultList

inline fun <reified T> SpringDataReactiveQueryFactory.selectQuery(noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(T::class.java, dsl)

inline fun <reified T : Any> SpringDataReactiveQueryFactory.updateQuery(noinline dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit) =
    updateQuery(T::class, dsl)

inline fun <reified T : Any> SpringDataReactiveQueryFactory.deleteQuery(noinline dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit) =
    deleteQuery(T::class, dsl)

inline fun <reified T> SpringDataReactiveQueryFactory.subquery(noinline dsl: SpringDataSubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)

inline fun <reified T> SpringDataReactiveQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataPageableQueryDsl<T>.() -> Unit
) = pageQuery(T::class.java, pageable, dsl)

inline fun <reified T> SpringDataReactiveQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
    noinline countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>,
) = pageQuery(T::class.java, pageable, dsl, countProjection)
