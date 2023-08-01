package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

interface UpdateQuerySetFirstStep<T : Any> {
    fun <V : Any, S : V?> set(path: Path<V>, value: S): UpdateQuerySetStep<T>

    fun <V : Any> set(path: Path<V>, value: Expressionable<V>): UpdateQuerySetStep<T>
}
