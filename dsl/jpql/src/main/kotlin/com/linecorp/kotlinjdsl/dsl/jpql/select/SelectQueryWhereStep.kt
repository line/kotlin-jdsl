package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryWhereStep<T> : SelectQueryGroupByStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun where(predicate: Predicate): SelectQueryGroupByStep<T>

    fun whereAnd(vararg predicates: Predicate): SelectQueryGroupByStep<T>

    fun whereAnd(predicates: Collection<Predicate>): SelectQueryGroupByStep<T>

    fun whereOr(vararg predicates: Predicate): SelectQueryGroupByStep<T>

    fun whereOr(predicates: Collection<Predicate>): SelectQueryGroupByStep<T>
}
