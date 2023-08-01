package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

interface UpdateQuerySetStep<T : Any> : UpdateQueryWhereStep<T>, JpqlQueryable<UpdateQuery<T>> {
    fun <V : Any, S : V?> set(path: Path<V>, value: S): UpdateQuerySetStep<T>

    fun <V : Any> set(path: Path<V>, value: Expressionable<V>): UpdateQuerySetStep<T>
}
