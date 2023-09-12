package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable

@SinceJdsl("3.0.0")
interface UpdateQuerySetFirstStep<T : Any> {
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
