package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that calculates the mod of a integer [value1] to a specified integer [value2].
 */
@Internal
data class JpqlMod internal constructor(
    val value1: Expression<Int>,
    val value2: Expression<Int>,
) : Expression<Int>
