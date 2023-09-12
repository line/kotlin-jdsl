package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

/**
 * Expression that divides [value1] by [value2].
 */
@Internal
data class JpqlDivide<T : Number> internal constructor(
    val value1: Expression<*>,
    val value2: Expression<*>,
) : Expression<T>
