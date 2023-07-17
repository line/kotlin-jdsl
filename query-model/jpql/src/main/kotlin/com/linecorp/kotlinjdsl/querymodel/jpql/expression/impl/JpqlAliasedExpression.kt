package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlAliasedExpression<T> internal constructor(
    val expression: Expression<T>,
    val alias: String,
) : Expression<T>
