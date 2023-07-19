package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlDivide<T : Number, S : T?> internal constructor(
    val value1: Expression<in S>,
    val value2: Expression<in S>,
) : Expression<T>
