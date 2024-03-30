package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that calculates the powering of a numeric [base] to a specified [exponent].
 */
@Internal
data class JpqlPower<T : Number> internal constructor(
    val base: Expression<T>,
    val exponent: Expression<T>,
) : Expression<Double>
