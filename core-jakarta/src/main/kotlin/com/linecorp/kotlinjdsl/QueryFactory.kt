package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kotlin.reflect.KClass

interface QueryFactory {
    fun <T> selectQuery(returnType: Class<T>, dsl: CriteriaQueryDsl<T>.() -> Unit): TypedQuery<T>
    fun <T : Any> updateQuery(target: KClass<T>, dsl: CriteriaUpdateQueryDsl.() -> Unit): Query
    fun <T : Any> deleteQuery(target: KClass<T>, dsl: CriteriaDeleteQueryDsl.() -> Unit): Query
    fun <T> subquery(returnType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit): SubqueryExpressionSpec<T>
}
