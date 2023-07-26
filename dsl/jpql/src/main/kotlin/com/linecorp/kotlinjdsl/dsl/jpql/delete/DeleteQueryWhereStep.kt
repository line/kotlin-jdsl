package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

interface DeleteQueryWhereStep<T : Any> : JpqlQueryable<DeleteQuery<T>> {
    fun where(predicate: Predicate?): JpqlQueryable<DeleteQuery<T>>

    fun whereAnd(vararg predicates: Predicate?): JpqlQueryable<DeleteQuery<T>>

    fun whereAnd(predicates: Iterable<Predicate?>): JpqlQueryable<DeleteQuery<T>>

    fun whereOr(vararg predicates: Predicate?): JpqlQueryable<DeleteQuery<T>>

    fun whereOr(predicates: Iterable<Predicate?>): JpqlQueryable<DeleteQuery<T>>
}
