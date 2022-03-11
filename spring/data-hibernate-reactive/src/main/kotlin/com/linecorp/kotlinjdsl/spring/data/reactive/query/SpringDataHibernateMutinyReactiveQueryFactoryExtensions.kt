package com.linecorp.kotlinjdsl.spring.data.reactive.query

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.deleteQuery
import com.linecorp.kotlinjdsl.spring.reactive.listQuery
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataPageableQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.singleQuery
import com.linecorp.kotlinjdsl.spring.reactive.updateQuery
import org.springframework.data.domain.Pageable


suspend inline fun <reified T> SpringDataHibernateMutinyReactiveQueryFactory.singleQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.singleQuery(dsl) }

suspend inline fun <reified T> SpringDataHibernateMutinyReactiveQueryFactory.listQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
) = withFactory { it.listQuery(dsl) }

suspend inline fun <reified T : Any> SpringDataHibernateMutinyReactiveQueryFactory.updateQuery(
    noinline dsl: CriteriaUpdateQueryDsl.() -> Unit
) = withFactory { it.updateQuery<T>(dsl).executeUpdate }

suspend inline fun <reified T : Any> SpringDataHibernateMutinyReactiveQueryFactory.deleteQuery(
    noinline dsl: CriteriaDeleteQueryDsl.() -> Unit
) = withFactory { it.deleteQuery<T>(dsl).executeUpdate }

inline fun <reified T> SpringDataHibernateMutinyReactiveQueryFactory.subquery(
    noinline dsl: SubqueryDsl<T>.() -> Unit
) = subquery(T::class.java, dsl)

suspend inline fun <reified T> SpringDataHibernateMutinyReactiveQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataPageableQueryDsl<T>.() -> Unit
) = withFactory { it.pageQuery(T::class.java, pageable, dsl) }

suspend inline fun <reified T> SpringDataHibernateMutinyReactiveQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
    noinline countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>
) = withFactory { it.pageQuery(T::class.java, pageable, dsl, countProjection) }
