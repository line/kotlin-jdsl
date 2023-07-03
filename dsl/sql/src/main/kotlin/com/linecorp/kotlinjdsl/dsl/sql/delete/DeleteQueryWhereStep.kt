package com.linecorp.kotlinjdsl.dsl.sql.delete

import com.linecorp.kotlinjdsl.query.sql.DeleteQuery
import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

interface DeleteQueryWhereStep<T : Any> : SqlQueryable<DeleteQuery<T>> {
    fun where(predicate: Predicate): SqlQueryable<DeleteQuery<T>>

    fun whereAnd(vararg predicates: Predicate): SqlQueryable<DeleteQuery<T>>
    fun whereAnd(predicates: Collection<Predicate>): SqlQueryable<DeleteQuery<T>>

    fun whereOr(vararg predicates: Predicate): SqlQueryable<DeleteQuery<T>>
    fun whereOr(predicates: Collection<Predicate>): SqlQueryable<DeleteQuery<T>>
}
