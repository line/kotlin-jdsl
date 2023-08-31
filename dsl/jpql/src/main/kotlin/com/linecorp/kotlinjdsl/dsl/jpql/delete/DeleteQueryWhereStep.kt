package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

@SinceJdsl("3.0.0")
interface DeleteQueryWhereStep<T : Any> : JpqlQueryable<DeleteQuery<T>> {
    @SinceJdsl("3.0.0")
    fun where(predicate: Predicatable?): JpqlQueryable<DeleteQuery<T>>

    @SinceJdsl("3.0.0")
    fun whereAnd(vararg predicates: Predicatable?): JpqlQueryable<DeleteQuery<T>>

    @SinceJdsl("3.0.0")
    fun whereOr(vararg predicates: Predicatable?): JpqlQueryable<DeleteQuery<T>>
}
