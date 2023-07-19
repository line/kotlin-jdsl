package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlCustomExpression<T> internal constructor(
    val template: String,
    val args: Iterable<Expression<*>>,
) : Expression<T>
