package com.linecorp.kotlinjdsl.spring.reactive

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.creator.ReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.reflect.KClass

class SpringDataReactiveQueryFactoryImpl(
    private val criteriaQueryCreator: ReactiveCriteriaQueryCreator,
    private val subqueryCreator: SubqueryCreator,
) : SpringDataReactiveQueryFactory {
    override fun <T> selectQuery(
        returnType: Class<T>,
        dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
    ): ReactiveQuery<T> {
        val criteriaQuerySpec = SpringDataReactiveQueryDslImpl(returnType).apply(dsl).createCriteriaQuerySpec()

        return criteriaQueryCreator.createQuery(criteriaQuerySpec)
    }

    override fun <T : Any> updateQuery(
        target: KClass<T>,
        dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit
    ): ReactiveQuery<T> {
        val criteriaQuerySpec = SpringDataReactiveQueryDslImpl(target.java).apply(dsl).apply {
            from(target)
        }.createCriteriaUpdateQuerySpec()

        return criteriaQueryCreator.createQuery(criteriaQuerySpec)
    }

    override fun <T : Any> deleteQuery(
        target: KClass<T>,
        dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit
    ): ReactiveQuery<T> {
        return criteriaQueryCreator.createQuery(
            SpringDataReactiveQueryDslImpl(target.java).apply(dsl).apply { from(target) }
                .createCriteriaDeleteQuerySpec()
        )
    }

    override fun <T> subquery(
        returnType: Class<T>,
        dsl: SpringDataSubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T> {
        val subquerySpec = SpringDataReactiveQueryDslImpl(returnType).apply(dsl).createSubquerySpec()

        return SubqueryExpressionSpec(subquerySpec, subqueryCreator)
    }

    override fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit
    ): CompletionStage<Page<T>> {
        val appliedDsl = SpringDataReactiveQueryDslImpl(returnType).apply { dsl(); this.pageable = pageable }

        val pageableQuery = criteriaQueryCreator.createQuery(appliedDsl.createPageableQuerySpec())
        return pageableQuery.resultList.thenCompose { pageList ->
            criteriaQueryCreator.createQuery(appliedDsl.createPageableCountQuerySpec()).resultList
                .thenCompose { pageableCountTotals ->
                    CompletableFuture.completedFuture(PageableExecutionUtils.getPage(pageList, pageable) {
                        executeCountQuery(pageableCountTotals)
                    })
                }
        }
    }

    override fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
        countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>,
    ): CompletionStage<Page<T>> {
        val appliedDsl = SpringDataReactiveQueryDslImpl(returnType).apply { dsl(); this.pageable = pageable }
        val countSelectClause = SpringDataReactiveQueryDslImpl(Long::class.java).run(countProjection)

        val pageableQuery = criteriaQueryCreator.createQuery(appliedDsl.createPageableQuerySpec())
        return pageableQuery.resultList.thenCompose { pageList ->
            criteriaQueryCreator.createQuery(appliedDsl.createPageableCountQuerySpec(countSelectClause)).resultList
                .thenCompose { pageableCountTotals ->
                    CompletableFuture.completedFuture(PageableExecutionUtils.getPage(pageList, pageable) {
                        executeCountQuery(pageableCountTotals)
                    })
                }
        }
    }

    private fun executeCountQuery(totals: List<Long>): Long {
        return if (totals.size == 1) {
            totals.first()
        } else {
            totals.count().toLong()
        }
    }
}
