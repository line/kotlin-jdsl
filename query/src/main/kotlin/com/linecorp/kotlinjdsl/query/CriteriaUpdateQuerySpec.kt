package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.set.SetClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface CriteriaUpdateQuerySpec<T, Q> {
    val targetEntity: Class<T>
    val from: FromClause<T>
    val associate: SimpleAssociatedJoinClause
    val where: CriteriaQueryWhereClause
    val jpaHint: JpaQueryHintClause<Q>
    val sqlHint: SqlQueryHintClause<Q>
    val set: SetClause
}
