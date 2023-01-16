package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.creator.ReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.*
import kotlin.reflect.KClass

class ReactiveQueryFactoryImpl(
    private val criteriaQueryCreator: ReactiveCriteriaQueryCreator,
    private val subqueryCreator: SubqueryCreator,
) : ReactiveQueryFactory {
    override fun <T> selectQuery(
        returnType: Class<T>,
        dsl: CriteriaQueryDsl<T>.() -> Unit
    ): ReactiveQuery<T> =
        criteriaQueryCreator.createQuery(ReactiveQueryDslImpl(returnType).apply(dsl).createCriteriaQuerySpec())

    override fun <T : Any> updateQuery(
        target: KClass<T>,
        dsl: CriteriaUpdateQueryDsl.() -> Unit
    ): ReactiveQuery<T> =
        criteriaQueryCreator.createQuery(
            ReactiveQueryDslImpl(target.java).apply(dsl).apply { from(target) }.createCriteriaUpdateQuerySpec()
        )

    override fun <T : Any> deleteQuery(
        target: KClass<T>,
        dsl: CriteriaDeleteQueryDsl.() -> Unit
    ): ReactiveQuery<T> =
        criteriaQueryCreator.createQuery(
            ReactiveQueryDslImpl(target.java).apply(dsl).apply { from(target) }.createCriteriaDeleteQuerySpec()
        )

    override fun <T> subquery(
        returnType: Class<T>,
        dsl: SubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T> =
        subquery(returnType, subqueryCreator, dsl)
}

fun <T> subquery(
    returnType: Class<T>,
    subqueryCreator: SubqueryCreator,
    dsl: SubqueryDsl<T>.() -> Unit
): SubqueryExpressionSpec<T> =
    SubqueryExpressionSpec(ReactiveQueryDslImpl(returnType).apply(dsl).createSubquerySpec(), subqueryCreator)
