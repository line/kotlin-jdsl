package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlAliasedExpression<T : Any> internal constructor(
    val expr: Expression<T>,
    val alias: Expression<T>,
) : Expression<T>
