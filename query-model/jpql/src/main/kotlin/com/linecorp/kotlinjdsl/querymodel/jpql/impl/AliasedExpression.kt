package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class AliasedExpression<T>(
    val expression: Expression<T>,
    val alias: String,
) : Expression<T>
