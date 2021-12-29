package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import javax.persistence.TypedQuery

class QueryFactoryImpl(
    private val criteriaQueryCreator: CriteriaQueryCreator,
    private val subqueryCreator: SubqueryCreator,
) : QueryFactory {
    override fun <T> typedQuery(
        returnType: Class<T>,
        dsl: CriteriaQueryDsl<T>.() -> Unit
    ): TypedQuery<T> {
        val criteriaQuerySpec = QueryDslImpl(returnType).apply(dsl).createCriteriaQuerySpec()

        return criteriaQueryCreator.createQuery(criteriaQuerySpec)
    }

    override fun <T> subquery(
        returnType: Class<T>,
        dsl: SubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T> {
        val subquerySpec = QueryDslImpl(returnType).apply(dsl).createSubquerySpec()

        return SubqueryExpressionSpec(subquerySpec, subqueryCreator)
    }
}