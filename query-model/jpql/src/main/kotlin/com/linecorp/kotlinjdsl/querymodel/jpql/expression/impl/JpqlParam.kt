package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlParam<T> internal constructor(
    val name: String,
    val value: T,
) : Expression<T & Any>
