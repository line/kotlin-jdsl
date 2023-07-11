package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class Min<T : Comparable<T>>(
    val expression: Expression<T?>,
    val distinct: Boolean,
) : Expression<T?>