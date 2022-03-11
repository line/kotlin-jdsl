package com.linecorp.kotlinjdsl.spring.reactive

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.*
import org.springframework.data.domain.Pageable

inline fun <reified T> SpringDataReactiveQueryFactory.singleQuery(
    noinline dsl: SpringDataReactiveCriteriaQueryDsl<T>.() -> Unit
) = selectQuery(dsl).singleResult

inline fun <reified T> SpringDataReactiveQueryFactory.singleQueryOrNull(
    noinline dsl: SpringDataReactiveCriteriaQueryDsl<T>.() -> Unit
) = selectQuery(dsl).singleResultOrNull

inline fun <reified T> SpringDataReactiveQueryFactory.listQuery(
    noinline dsl: SpringDataReactiveCriteriaQueryDsl<T>.() -> Unit
) = selectQuery(dsl).resultList

inline fun <reified T> SpringDataReactiveQueryFactory.selectQuery(noinline dsl: SpringDataReactiveCriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(T::class.java, dsl)

inline fun <reified T : Any> SpringDataReactiveQueryFactory.updateQuery(noinline dsl: SpringDataReactiveCriteriaUpdateQueryDsl.() -> Unit) =
    updateQuery(T::class, dsl)

inline fun <reified T : Any> SpringDataReactiveQueryFactory.deleteQuery(noinline dsl: SpringDataReactiveCriteriaDeleteQueryDsl.() -> Unit) =
    deleteQuery(T::class, dsl)

inline fun <reified T> SpringDataReactiveQueryFactory.subquery(noinline dsl: SpringDataReactiveSubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)

inline fun <reified T> SpringDataReactiveQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataReactivePageableQueryDsl<T>.() -> Unit
) = pageQuery(T::class.java, pageable, dsl)

inline fun <reified T> SpringDataReactiveQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataReactivePageableQueryDsl<T>.() -> Unit,
    noinline countProjection: SpringDataReactivePageableQueryDsl<Long>.() -> SingleSelectClause<Long>,
) = pageQuery(T::class.java, pageable, dsl, countProjection)
