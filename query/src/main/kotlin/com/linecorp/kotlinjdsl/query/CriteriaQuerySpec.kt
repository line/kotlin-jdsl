package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.CriteriaQueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.CriteriaQueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.CriteriaQuerySelectClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface CriteriaQuerySpec<T, Q> {
    val select: CriteriaQuerySelectClause<T>
    val from: FromClause<*>
    val join: JoinClause
    val where: CriteriaQueryWhereClause
    val groupBy: CriteriaQueryGroupByClause
    val having: CriteriaQueryHavingClause
    val orderBy: CriteriaQueryOrderByClause
    val limit: QueryLimitClause<Q>
    val jpaHint: JpaQueryHintClause<Q>
    val sqlHint: SqlQueryHintClause<Q>
}
