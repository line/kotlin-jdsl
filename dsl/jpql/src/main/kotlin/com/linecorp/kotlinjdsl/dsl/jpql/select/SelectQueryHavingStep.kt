package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryHavingStep : SelectQueryOrderByStep, JpqlQueryable<SelectQuery> {
    fun having(predicate: Predicate): SelectQueryOrderByStep

    fun havingAnd(vararg predicates: Predicate): SelectQueryOrderByStep

    fun havingAnd(predicates: Collection<Predicate>): SelectQueryOrderByStep

    fun havingOr(vararg predicates: Predicate): SelectQueryOrderByStep

    fun havingOr(predicates: Collection<Predicate>): SelectQueryOrderByStep
}
