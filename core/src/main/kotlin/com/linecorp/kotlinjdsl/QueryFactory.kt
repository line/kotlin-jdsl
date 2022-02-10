package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

interface QueryFactory {
    @Deprecated(replaceWith = ReplaceWith(expression = "selectQuery"), message = "This method has been replaced with selectQuery.")
    fun <T> typedQuery(returnType: Class<T>, dsl: CriteriaQueryDsl<T>.() -> Unit) = selectQuery(returnType, dsl)
    fun <T> selectQuery(returnType: Class<T>, dsl: CriteriaQueryDsl<T>.() -> Unit): TypedQuery<T>
    fun <T: Any> updateQuery(target: KClass<T>, dsl: CriteriaUpdateQueryDsl.() -> Unit): Query
    fun <T> subquery(returnType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit): SubqueryExpressionSpec<T>
}
