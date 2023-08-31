package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

@SinceJdsl("3.0.0")
interface UpdateQueryWhereStep<T : Any> : JpqlQueryable<UpdateQuery<T>> {
    @SinceJdsl("3.0.0")
    fun where(predicate: Predicatable?): JpqlQueryable<UpdateQuery<T>>

    @SinceJdsl("3.0.0")
    fun whereAnd(vararg predicates: Predicatable?): JpqlQueryable<UpdateQuery<T>>

    @SinceJdsl("3.0.0")
    fun whereOr(vararg predicates: Predicatable?): JpqlQueryable<UpdateQuery<T>>
}
