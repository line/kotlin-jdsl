package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

interface UpdateQueryWhereStep<T : Any> : JpqlQueryable<UpdateQuery<T>> {
    fun where(predicate: Predicatable?): JpqlQueryable<UpdateQuery<T>>

    fun whereAnd(vararg predicates: Predicatable?): JpqlQueryable<UpdateQuery<T>>

    fun whereOr(vararg predicates: Predicatable?): JpqlQueryable<UpdateQuery<T>>
}
