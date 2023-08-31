package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlExpressionParentheses<T : Any> internal constructor(
    val expr: Expression<T>,
) : Expression<T>
