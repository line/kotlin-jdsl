package com.linecorp.kotlinjdsl.spring.reactive

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.concurrent.CompletionStage
import kotlin.reflect.KClass

interface SpringDataReactiveQueryFactory {
    fun <T> selectQuery(
        returnType: Class<T>,
        dsl: SpringDataReactiveCriteriaQueryDsl<T>.() -> Unit
    ): ReactiveQuery<T>

    fun <T : Any> updateQuery(
        target: KClass<T>,
        dsl: SpringDataReactiveCriteriaUpdateQueryDsl.() -> Unit
    ): ReactiveQuery<T>

    fun <T : Any> deleteQuery(
        target: KClass<T>,
        dsl: SpringDataReactiveCriteriaDeleteQueryDsl.() -> Unit
    ): ReactiveQuery<T>

    fun <T> subquery(
        returnType: Class<T>,
        dsl: SpringDataReactiveSubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T>

    fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataReactivePageableQueryDsl<T>.() -> Unit
    ): CompletionStage<Page<T>>

    fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataReactivePageableQueryDsl<T>.() -> Unit,
        countProjection: SpringDataReactivePageableQueryDsl<Long>.() -> SingleSelectClause<Long>
    ): CompletionStage<Page<T>>
}
