package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlTrimBoth internal constructor(
    val character: Expression<Char>?,
    val value: Expression<String>,
) : Expression<String>
