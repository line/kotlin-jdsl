package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.*

/**
 * Expression to test if the comparison operation is true for some value in the result of the [subquery].
 *
 * The comparison operators used with this expression are
 * - [JpqlEqual]
 * - [JpqlNotEqual]
 * - [JpqlLessThan]
 * - [JpqlLessThanOrEqualTo]
 * - [JpqlGreaterThan]
 * - [JpqlGreaterThanOrEqualTo]
 */
@Internal
data class JpqlAny<T : Any> internal constructor(
    val subquery: Subquery<T>,
) : Expression<T>
