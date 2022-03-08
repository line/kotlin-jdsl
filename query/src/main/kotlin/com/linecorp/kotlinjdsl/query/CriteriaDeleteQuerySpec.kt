package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
interface CriteriaDeleteQuerySpec<T, Q> {
    val targetEntity: Class<T>
    val from: FromClause<T>
    val associate: SimpleAssociatedJoinClause
    val where: CriteriaQueryWhereClause
    val jpaHint: JpaQueryHintClause<Q>
    val sqlHint: SqlQueryHintClause<Q>
}
