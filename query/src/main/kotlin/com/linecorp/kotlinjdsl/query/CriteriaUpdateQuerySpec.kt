package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec

interface CriteriaUpdateQuerySpec<T> {
    val targetEntity: Class<T>
    val from: FromClause
    val join: JoinClause
    val where: CriteriaQueryWhereClause
    val jpaHint: JpaQueryHintClause
    val sqlHint: SqlQueryHintClause
    val params: Map<ColumnSpec<*>, Any?>
}
