package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlNullIf<T : Any> internal constructor(
    val value: Expression<T>,
    val compareValue: Expression<T>,
) : Expression<T>
