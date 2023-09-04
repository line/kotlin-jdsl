package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

@SinceJdsl("3.0.0")
interface UpdateQuerySetStep<T : Any> : UpdateQueryWhereStep<T>, JpqlQueryable<UpdateQuery<T>> {
    /**
     * Creates a set clause in an update query.
     */
    @SinceJdsl("3.0.0")
    fun <V : Any, S : V?> set(path: Pathable<V>, value: S): UpdateQuerySetStep<T>

    /**
     * Creates a set clause in an update query.
     */
    @SinceJdsl("3.0.0")
    fun <V : Any> set(path: Pathable<V>, value: Expressionable<V>): UpdateQuerySetStep<T>
}
