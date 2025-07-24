package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlLeft internal constructor(
    val value: Expression<String>,
    val length: Expression<Int>,
) : Expression<String>
