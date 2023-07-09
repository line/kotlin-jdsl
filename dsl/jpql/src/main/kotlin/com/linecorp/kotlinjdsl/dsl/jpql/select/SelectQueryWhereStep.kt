package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryWhereStep : SelectQueryGroupByStep, JpqlQueryable<SelectQuery> {
    fun where(predicate: Predicate): SelectQueryGroupByStep

    fun whereAnd(vararg predicates: Predicate): SelectQueryGroupByStep

    fun whereAnd(predicates: Collection<Predicate>): SelectQueryGroupByStep

    fun whereOr(vararg predicates: Predicate): SelectQueryGroupByStep

    fun whereOr(predicates: Collection<Predicate>): SelectQueryGroupByStep
}
