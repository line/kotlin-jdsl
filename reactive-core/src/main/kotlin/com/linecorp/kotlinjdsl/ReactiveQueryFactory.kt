package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.creator.ReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import kotlin.reflect.KClass

interface ReactiveQueryFactory {
    fun <T> selectQuery(
        returnType: Class<T>,
        dsl: CriteriaQueryDsl<T>.() -> Unit
    ): ReactiveQuery<T>

    fun <T : Any> updateQuery(
        target: KClass<T>,
        dsl: CriteriaUpdateQueryDsl.() -> Unit
    ): ReactiveQuery<T>

    fun <T : Any> deleteQuery(
        target: KClass<T>,
        dsl: CriteriaDeleteQueryDsl.() -> Unit
    ): ReactiveQuery<T>

    fun <T> subquery(
        returnType: Class<T>,
        dsl: SubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T>
}
