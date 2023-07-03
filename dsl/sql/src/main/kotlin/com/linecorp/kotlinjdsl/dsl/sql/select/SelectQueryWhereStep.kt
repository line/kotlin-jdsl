package com.linecorp.kotlinjdsl.dsl.sql.select

import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.SelectQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

interface SelectQueryWhereStep : SelectQueryGroupByStep, SqlQueryable<SelectQuery> {
    fun where(predicate: Predicate): SelectQueryGroupByStep

    fun whereAnd(vararg predicates: Predicate): SelectQueryGroupByStep
    fun whereAnd(predicates: Collection<Predicate>): SelectQueryGroupByStep

    fun whereOr(vararg predicates: Predicate): SelectQueryGroupByStep
    fun whereOr(predicates: Collection<Predicate>): SelectQueryGroupByStep
}
