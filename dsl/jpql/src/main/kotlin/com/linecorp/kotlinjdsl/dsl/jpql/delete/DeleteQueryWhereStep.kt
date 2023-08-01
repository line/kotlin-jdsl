package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

interface DeleteQueryWhereStep<T : Any> : JpqlQueryable<DeleteQuery<T>> {
    fun where(predicate: Predicatable?): JpqlQueryable<DeleteQuery<T>>

    fun whereAnd(vararg predicates: Predicatable?): JpqlQueryable<DeleteQuery<T>>

    fun whereOr(vararg predicates: Predicatable?): JpqlQueryable<DeleteQuery<T>>
}
