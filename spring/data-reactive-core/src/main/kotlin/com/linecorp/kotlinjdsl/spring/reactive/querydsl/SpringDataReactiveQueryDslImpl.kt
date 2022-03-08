package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.ReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.hint.JpaReactiveQueryHintClauseImpl
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import com.linecorp.kotlinjdsl.query.clause.limit.ReactiveLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.*
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.limit.SpringDataPageableLimitClause
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.orderby.SpringDataPageableOrderByClause
import org.springframework.data.domain.Pageable

/**
 * Internal DSL Implementation which is integrated Spring Data JPA
 *
 * Don't use this directly because it's an <string>INTERNAL</strong> class.
 * It does not support backward compatibility.
 */
class SpringDataReactiveQueryDslImpl<T>(
    returnType: Class<T>,
) : ReactiveQueryDslImpl<T>(returnType),
    SpringDataCriteriaQueryDsl<T>, SpringDataSubqueryDsl<T>, SpringDataPageableQueryDsl<T>,
    SpringDataCriteriaUpdateQueryDsl, SpringDataCriteriaDeleteQueryDsl {
    var pageable: Pageable = Pageable.unpaged()

    fun createPageableQuerySpec(): CriteriaQuerySpec<T, ReactiveQuery<T>> {
        return CriteriaQuerySpecImpl(
            select = getCriteriaQuerySelectClause(),
            from = getFromClause(),
            join = getJoinClauseDoesNotHaveFetch(),
            where = getWhereClause(),
            groupBy = getEmptyGroupByClause(),
            having = getEmptyHavingClause(),
            orderBy = getPageableOrderByClause(),
            limit = getPageableLimitClause(),
            sqlHint = getSqlQueryHintClause(),
            jpaHint = getJpaQueryHintClause(),
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun createPageableCountQuerySpec(countSelectClause: SingleSelectClause<Long>? = null): CriteriaQuerySpec<Long, ReactiveQuery<Long>> {
        return CriteriaQuerySpecImpl(
            select = countSelectClause ?: getCriteriaCountQuerySelectClause(),
            from = getFromClause(),
            join = getJoinClauseDoesNotHaveFetch(),
            where = getWhereClause(),
            groupBy = getEmptyGroupByClause(),
            having = getEmptyHavingClause(),
            orderBy = getEmptyOrderByClause(),
            limit = getEmptyLimitClause() as ReactiveLimitClause<Long>,
            jpaHint = getJpaQueryHintClause() as JpaReactiveQueryHintClauseImpl<Long>,
            sqlHint = getSqlQueryHintClause() as SqlQueryHintClause<ReactiveQuery<Long>>,
        )
    }

    private fun getCriteriaCountQuerySelectClause(): CriteriaQuerySelectClause<Long> {
        return when (val selectClause = getCriteriaQuerySelectClause()) {
            is SingleSelectClause -> CountSingleSelectClause(selectClause.distinct, selectClause.expression)
            is MultiSelectClause -> CountMultiSelectClause(selectClause.distinct, selectClause.expressions)
            else -> throw IllegalStateException("Pageable query does not support $selectClause")
        }
    }

    private fun getPageableOrderByClause(): CriteriaQueryOrderByClause {
        return SpringDataPageableOrderByClause(pageable)
    }

    private fun <Q> getPageableLimitClause(): QueryLimitClause<ReactiveQuery<Q>> {
        return SpringDataPageableLimitClause(pageable)
    }
}
