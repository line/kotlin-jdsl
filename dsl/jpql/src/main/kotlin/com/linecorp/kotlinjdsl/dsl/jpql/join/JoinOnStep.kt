package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

interface JoinOnStep<T : Any> {
    fun on(predicate: Predicatable): JoinAsStep<T>
}
