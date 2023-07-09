package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

/**
 * Expression that returns the first non-null value in the expressions,
 * or null if there are no non-null value in expressions.
 */
data class Coalesce<T>(
    val expressions: Collection<Expression<in T>>,
) : Expression<T>
