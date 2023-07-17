package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlMax<T : Comparable<T>> internal constructor(
    val expression: Expression<T?>,
    val distinct: Boolean,
) : Expression<T?>
