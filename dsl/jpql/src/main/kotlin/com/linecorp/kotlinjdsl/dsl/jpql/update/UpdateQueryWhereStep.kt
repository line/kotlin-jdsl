package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

interface UpdateQueryWhereStep<T : Any> : JpqlQueryable<UpdateQuery<T>> {
    fun where(predicate: Predicate): JpqlQueryable<UpdateQuery<T>>

    fun whereAnd(vararg predicates: Predicate?): JpqlQueryable<UpdateQuery<T>>

    fun whereAnd(predicates: Iterable<Predicate?>): JpqlQueryable<UpdateQuery<T>>

    fun whereOr(vararg predicates: Predicate?): JpqlQueryable<UpdateQuery<T>>

    fun whereOr(predicates: Iterable<Predicate?>): JpqlQueryable<UpdateQuery<T>>
}
