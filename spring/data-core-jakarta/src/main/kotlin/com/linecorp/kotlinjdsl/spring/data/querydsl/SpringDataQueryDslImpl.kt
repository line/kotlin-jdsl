package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.*
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.spring.data.query.clause.limit.SpringDataPageableLimitClause
import com.linecorp.kotlinjdsl.spring.data.query.clause.orderby.SpringDataPageableOrderByClause
import org.springframework.data.domain.Pageable
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery

/**
 * Internal DSL Implementation which is integrated Spring Data JPA
 *
 * Don't use this directly because it's an **INTERNAL** class.
 * It does not support backward compatibility.
 */
class SpringDataQueryDslImpl<T>(
    returnType: Class<T>,
) : QueryDslImpl<T>(returnType),
    SpringDataCriteriaQueryDsl<T>, SpringDataSubqueryDsl<T>, SpringDataPageableQueryDsl<T>,
    SpringDataCriteriaUpdateQueryDsl, SpringDataCriteriaDeleteQueryDsl {
    var pageable: Pageable = Pageable.unpaged()

    fun createPageableQuerySpec(): CriteriaQuerySpec<T, TypedQuery<T>> {
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

    fun createPageableCountQuerySpec(countSelectClause: SingleSelectClause<Long>? = null): CriteriaQuerySpec<Long, TypedQuery<Long>> {
        return CriteriaQuerySpecImpl(
            select = countSelectClause ?: getCriteriaCountQuerySelectClause(),
            from = getFromClause(),
            join = getJoinClauseDoesNotHaveFetch(),
            where = getWhereClause(),
            groupBy = getEmptyGroupByClause(),
            having = getEmptyHavingClause(),
            orderBy = getEmptyOrderByClause(),
            limit = getEmptyLimitClause(),
            sqlHint = getSqlQueryHintClause(),
            jpaHint = getJpaQueryHintClause(),
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

    private fun <Q : Query> getPageableLimitClause(): QueryLimitClause<Q> {
        return SpringDataPageableLimitClause(pageable)
    }
}
