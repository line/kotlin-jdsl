package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that returns the first non-null value in the expressions,
 * or null if there are no non-null value in expressions.
 */
@Internal
data class JpqlCoalesce<T> internal constructor(
    val expr: Iterable<Expression<in T>>,
) : Expression<T>
