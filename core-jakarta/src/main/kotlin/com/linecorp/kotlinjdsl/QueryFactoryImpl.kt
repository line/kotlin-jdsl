package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.*
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kotlin.reflect.KClass

class QueryFactoryImpl(
    private val criteriaQueryCreator: CriteriaQueryCreator,
    private val subqueryCreator: SubqueryCreator,
) : QueryFactory {
    override fun <T> selectQuery(
        returnType: Class<T>,
        dsl: CriteriaQueryDsl<T>.() -> Unit
    ): TypedQuery<T> {
        val criteriaQuerySpec = QueryDslImpl(returnType).apply(dsl).createCriteriaQuerySpec()

        return criteriaQueryCreator.createQuery(criteriaQuerySpec)
    }

    override fun <T : Any> updateQuery(target: KClass<T>, dsl: CriteriaUpdateQueryDsl.() -> Unit): Query {
        return criteriaQueryCreator.createQuery(
            QueryDslImpl(target.java).apply(dsl).apply { from(target) }.createCriteriaUpdateQuerySpec()
        )
    }

    override fun <T : Any> deleteQuery(target: KClass<T>, dsl: CriteriaDeleteQueryDsl.() -> Unit): Query {
        return criteriaQueryCreator.createQuery(
            QueryDslImpl(target.java).apply(dsl).apply { from(target) }.createCriteriaDeleteQuerySpec()
        )
    }

    override fun <T> subquery(
        returnType: Class<T>,
        dsl: SubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T> {
        val subquerySpec = QueryDslImpl(returnType).apply(dsl).createSubquerySpec()

        return SubqueryExpressionSpec(subquerySpec, subqueryCreator)
    }
}
