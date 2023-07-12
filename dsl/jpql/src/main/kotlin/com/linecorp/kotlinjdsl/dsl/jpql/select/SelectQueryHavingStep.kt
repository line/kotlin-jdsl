package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryHavingStep<T> : SelectQueryOrderByStep<T>, JpqlQueryable<SelectQuery<T>> {
    fun having(predicate: Predicate): SelectQueryOrderByStep<T>

    fun havingAnd(vararg predicates: Predicate): SelectQueryOrderByStep<T>

    fun havingAnd(predicates: Collection<Predicate>): SelectQueryOrderByStep<T>

    fun havingOr(vararg predicates: Predicate): SelectQueryOrderByStep<T>

    fun havingOr(predicates: Collection<Predicate>): SelectQueryOrderByStep<T>
}
