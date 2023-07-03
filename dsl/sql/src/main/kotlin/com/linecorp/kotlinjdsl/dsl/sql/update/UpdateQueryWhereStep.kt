package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable
import com.linecorp.kotlinjdsl.query.sql.UpdateQuery

interface UpdateQueryWhereStep<T : Any> : SqlQueryable<UpdateQuery<T>> {
    fun where(predicate: Predicate): SqlQueryable<UpdateQuery<T>>

    fun whereAnd(vararg predicates: Predicate): SqlQueryable<UpdateQuery<T>>
    fun whereAnd(predicates: Collection<Predicate>): SqlQueryable<UpdateQuery<T>>

    fun whereOr(vararg predicates: Predicate): SqlQueryable<UpdateQuery<T>>
    fun whereOr(predicates: Collection<Predicate>): SqlQueryable<UpdateQuery<T>>
}
