package com.linecorp.kotlinjdsl.dsl.sql.select

import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

interface SelectQueryHavingStep : SelectQueryOrderByStep, SqlQueryable<SelectQuery> {
    fun having(predicate: Predicate): SelectQueryOrderByStep

    fun havingAnd(vararg predicates: Predicate): SelectQueryOrderByStep
    fun havingAnd(predicates: Collection<Predicate>): SelectQueryOrderByStep

    fun havingOr(vararg predicates: Predicate): SelectQueryOrderByStep
    fun havingOr(predicates: Collection<Predicate>): SelectQueryOrderByStep
}
