package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface Pathable<T : Any> : Expressionable<T> {
    @SinceJdsl("3.0.0")
    fun toPath(): Path<T>
}
