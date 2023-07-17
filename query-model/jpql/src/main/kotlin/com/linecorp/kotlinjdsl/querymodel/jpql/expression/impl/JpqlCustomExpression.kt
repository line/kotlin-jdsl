package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlCustomExpression<T> internal constructor(
    val template: String,
    val args: Collection<Expression<*>>,
) : Expression<T>
