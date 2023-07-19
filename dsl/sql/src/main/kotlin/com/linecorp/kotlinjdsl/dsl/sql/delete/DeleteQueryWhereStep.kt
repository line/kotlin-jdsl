package com.linecorp.kotlinjdsl.dsl.sql.delete

import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable

interface DeleteQueryWhereStep<T : Any> : SqlQueryable<com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery<T>> {
    fun where(predicate: Predicate): SqlQueryable<com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery<T>>

    fun whereAnd(vararg predicates: Predicate): SqlQueryable<com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery<T>>
    fun whereAnd(predicates: Iterable<Predicate>): SqlQueryable<com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery<T>>

    fun whereOr(vararg predicates: Predicate): SqlQueryable<com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery<T>>
    fun whereOr(predicates: Iterable<Predicate>): SqlQueryable<com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery<T>>
}
