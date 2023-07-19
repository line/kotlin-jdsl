package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable
import com.linecorp.kotlinjdsl.querymodel.sql.UpdateQuery

interface UpdateQueryWhereStep<T : Any> : SqlQueryable<UpdateQuery<T>> {
    fun where(predicate: Predicate): SqlQueryable<UpdateQuery<T>>

    fun whereAnd(vararg predicates: Predicate): SqlQueryable<UpdateQuery<T>>
    fun whereAnd(predicates: Iterable<Predicate>): SqlQueryable<UpdateQuery<T>>

    fun whereOr(vararg predicates: Predicate): SqlQueryable<UpdateQuery<T>>
    fun whereOr(predicates: Iterable<Predicate>): SqlQueryable<UpdateQuery<T>>
}
