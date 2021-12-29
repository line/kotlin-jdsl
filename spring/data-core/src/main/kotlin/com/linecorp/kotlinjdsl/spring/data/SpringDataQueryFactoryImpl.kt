package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataPageableQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataSubqueryDsl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import javax.persistence.TypedQuery

class SpringDataQueryFactoryImpl(
    private val criteriaQueryCreator: CriteriaQueryCreator,
    private val subqueryCreator: SubqueryCreator,
) : SpringDataQueryFactory {
    override fun <T> typedQuery(
        returnType: Class<T>,
        dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
    ): TypedQuery<T> {
        val criteriaQuerySpec = SpringDataQueryDslImpl(returnType).apply(dsl).createCriteriaQuerySpec()

        return criteriaQueryCreator.createQuery(criteriaQuerySpec)
    }

    override fun <T> subquery(
        returnType: Class<T>,
        dsl: SpringDataSubqueryDsl<T>.() -> Unit
    ): SubqueryExpressionSpec<T> {
        val subquerySpec = SpringDataQueryDslImpl(returnType).apply(dsl).createSubquerySpec()

        return SubqueryExpressionSpec(subquerySpec, subqueryCreator)
    }

    override fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit
    ): Page<T> {
        val appliedDsl = SpringDataQueryDslImpl(returnType).apply { dsl(); this.pageable = pageable }

        val pageableQuery = criteriaQueryCreator.createQuery(appliedDsl.createPageableQuerySpec())
        val pageableCountQuery = criteriaQueryCreator.createQuery(appliedDsl.createPageableCountQuerySpec())

        return PageableExecutionUtils.getPage(pageableQuery.resultList, pageable) {
            executeCountQuery(pageableCountQuery)
        }
    }

    override fun <T> pageQuery(
        returnType: Class<T>,
        pageable: Pageable,
        dsl: SpringDataPageableQueryDsl<T>.() -> Unit,
        countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long>,
    ): Page<T> {
        val appliedDsl = SpringDataQueryDslImpl(returnType).apply { dsl(); this.pageable = pageable }
        val countSelectClause = SpringDataQueryDslImpl(Long::class.java).run(countProjection)

        val pageableQuery = criteriaQueryCreator.createQuery(appliedDsl.createPageableQuerySpec())
        val pageableCountQuery = criteriaQueryCreator.createQuery(
            appliedDsl.createPageableCountQuerySpec(countSelectClause)
        )

        return PageableExecutionUtils.getPage(pageableQuery.resultList, pageable) {
            executeCountQuery(pageableCountQuery)
        }
    }

    private fun executeCountQuery(query: TypedQuery<Long>): Long {
        val totals = query.resultList

        return if (totals.size == 1) {
            totals.first()
        } else {
            totals.count().toLong()
        }
    }
}