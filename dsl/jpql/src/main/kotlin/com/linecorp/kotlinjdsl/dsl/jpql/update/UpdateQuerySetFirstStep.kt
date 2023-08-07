package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable

interface UpdateQuerySetFirstStep<T : Any> {
    fun <V : Any, S : V?> set(path: Pathable<V>, value: S): UpdateQuerySetStep<T>

    fun <V : Any> set(path: Pathable<V>, value: Expressionable<V>): UpdateQuerySetStep<T>
}
