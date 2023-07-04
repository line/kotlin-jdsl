package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Predicate

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
