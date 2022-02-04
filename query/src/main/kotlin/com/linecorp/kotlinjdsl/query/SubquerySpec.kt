package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.SubqueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.SubqueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.select.SubquerySelectClause
import com.linecorp.kotlinjdsl.query.clause.where.SubqueryWhereClause

interface SubquerySpec<T> {
    val select: SubquerySelectClause<T>
    val from: FromClause
    val join: JoinClause
    val where: SubqueryWhereClause
    val groupBy: SubqueryGroupByClause
    val having: SubqueryHavingClause
}