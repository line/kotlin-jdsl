package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kotlin.reflect.KClass

interface SpringDataQueryFactory {
    @Deprecated(
        replaceWith = ReplaceWith(expression = "selectQuery"),
        message = "This method has been replaced with selectQuery."
    )
    fun <T> typedQuery(returnType: Class<T>, dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit): TypedQuery<T> =
        selectQuery(returnType, dsl)

    fun <T> selectQuery(returnType: Class<T>, dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit): TypedQuery<T>
    fun <T : Any> updateQuery(target: KClass<T>, dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit): Query
    fun <T : Any> deleteQuery(target: KClass<T>, dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit): Query
    fun <T> subquery(returnType: Class<T>, dsl: SpringDataSubqueryDsl<T>.() -> Unit): SubqueryExpressionSpec<T>
    fun <T> pageQuery(returnType: Class<T>, pageable: Pageable, dsl: SpringDataPageableQueryDsl<T>.() -> Unit): Page<T>
    fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
        countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>
    ): Page<T>
}
