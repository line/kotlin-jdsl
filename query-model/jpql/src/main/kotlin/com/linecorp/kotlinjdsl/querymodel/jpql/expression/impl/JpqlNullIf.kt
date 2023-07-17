package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlNullIf<T> internal constructor(
    val left: Expression<T>,
    val right: Expression<T>,
) : Expression<T?>
