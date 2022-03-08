package com.linecorp.kotlinjdsl.spring.reactive

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.reflect.KClass

interface SpringDataReactiveQueryFactory {
    fun <T> selectQuery(
        returnType: Class<T>,
        dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
    ): ReactiveQuery<T>

    fun <T : Any> updateQuery(
        target: KClass<T>,
        dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit
    ): ReactiveQuery<T>

    fun <T : Any> deleteQuery(
        target: KClass<T>,
        dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit
    ): ReactiveQuery<T>

    fun <T> subquery(
        returnType: Class<T>,
        dsl: SpringDataSubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T>

    fun <T> pageQuery(returnType: Class<T>, pageable: Pageable, dsl: SpringDataPageableQueryDsl<T>.() -> Unit): CompletionStage<Page<T>>

    fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
        countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>
    ): CompletionStage<Page<T>>
}
