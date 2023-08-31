package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

@SinceJdsl("3.0.0")
interface JoinOnStep<T : Any> {
    @SinceJdsl("3.0.0")
    fun on(predicate: Predicatable): JoinAsStep<T>
}
