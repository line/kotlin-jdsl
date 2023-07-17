package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlValue<T> internal constructor(
    val value: T,
) : Expression<T>
