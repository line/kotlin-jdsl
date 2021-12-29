package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import javax.persistence.TypedQuery

interface QueryFactory {
    fun <T> typedQuery(returnType: Class<T>, dsl: CriteriaQueryDsl<T>.() -> Unit): TypedQuery<T>
    fun <T> subquery(returnType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit): SubqueryExpressionSpec<T>
}