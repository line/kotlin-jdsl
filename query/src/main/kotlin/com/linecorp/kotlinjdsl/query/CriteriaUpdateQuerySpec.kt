package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.set.SetClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause

interface CriteriaUpdateQuerySpec<T> {
    val targetEntity: Class<T>
    val from: FromClause
    val associate: SimpleAssociatedJoinClause
    val where: CriteriaQueryWhereClause
    val jpaHint: JpaQueryHintClause
    val sqlHint: SqlQueryHintClause
    val set: SetClause
}
