package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlSubstring internal constructor(
    val value: Expression<String>,
    val start: Expression<Int>,
    val length: Expression<Int>?,
) : Expression<String>
