package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

@Internal
data class Case<T>(
    val whens: Collection<When<T>>,
    val `else`: Expression<out T>?,
) : Expression<T> {
    data class When<T>(
        val predicate: Predicate,
        val then: Expression<T>,
    )
}
