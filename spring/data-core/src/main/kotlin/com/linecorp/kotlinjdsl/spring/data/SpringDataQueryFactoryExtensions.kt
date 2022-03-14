package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.spring.data.querydsl.*
import org.springframework.data.domain.Pageable
import java.util.stream.Stream

inline fun <reified T> SpringDataQueryFactory.singleQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
): T = selectQuery(T::class.java, dsl).singleResult

inline fun <reified T> SpringDataQueryFactory.listQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
): List<T> = selectQuery(T::class.java, dsl).resultList

inline fun <reified T> SpringDataQueryFactory.streamQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
): Stream<T> = selectQuery(T::class.java, dsl).resultStream

@Deprecated(
    replaceWith = ReplaceWith(expression = "selectQuery"),
    message = "This method has been replaced with selectQuery."
)
inline fun <reified T> SpringDataQueryFactory.typedQuery(noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(dsl)

inline fun <reified T> SpringDataQueryFactory.selectQuery(noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit) =
    selectQuery(T::class.java, dsl)

inline fun <reified T : Any> SpringDataQueryFactory.updateQuery(noinline dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit) =
    updateQuery(T::class, dsl)

inline fun <reified T : Any> SpringDataQueryFactory.deleteQuery(noinline dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit) =
    deleteQuery(T::class, dsl)

inline fun <reified T> SpringDataQueryFactory.subquery(noinline dsl: SpringDataSubqueryDsl<T>.() -> Unit) =
    subquery(T::class.java, dsl)

inline fun <reified T> SpringDataQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataPageableQueryDsl<T>.() -> Unit
) = pageQuery(T::class.java, pageable, dsl)

inline fun <reified T> SpringDataQueryFactory.pageQuery(
    pageable: Pageable,
    noinline dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
    noinline countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>,
) = pageQuery(T::class.java, pageable, dsl, countProjection)
