package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause

interface CriteriaMutableQuerySpec<T> {
    val targetEntity: Class<T>
    val from: FromClause
    val associate: SimpleAssociatedJoinClause
    val where: CriteriaQueryWhereClause
    val jpaHint: JpaQueryHintClause
    val sqlHint: SqlQueryHintClause
}

/**
 * According to the current specification, CriteriaMutableQuerySpec and CriteriaDeleteQuerySpec are the same.
 * so instead of creating an interface, create a typealias.
 * If the specifications change in the future, separate them into separate interfaces.
 * This is because CriteriaUpdateQuerySpec inherits from CriteriaMutableQuerySpec.
 */
typealias CriteriaDeleteQuerySpec<T> = CriteriaMutableQuerySpec<T>
