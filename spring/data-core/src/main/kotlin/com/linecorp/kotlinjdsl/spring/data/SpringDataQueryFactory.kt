package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataPageableQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataSubqueryDsl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import javax.persistence.TypedQuery

interface SpringDataQueryFactory {
    fun <T> typedQuery(returnType: Class<T>, dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit): TypedQuery<T>
    fun <T> subquery(returnType: Class<T>, dsl: SpringDataSubqueryDsl<T>.() -> Unit): SubqueryExpressionSpec<T>
    fun <T> pageQuery(returnType: Class<T>, pageable: Pageable, dsl: SpringDataPageableQueryDsl<T>.() -> Unit): Page<T>
    fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
        countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>
    ): Page<T>
}