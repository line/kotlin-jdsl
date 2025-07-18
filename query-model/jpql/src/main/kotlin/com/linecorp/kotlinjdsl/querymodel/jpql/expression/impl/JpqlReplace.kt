package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlReplace internal constructor(
    val value: Expression<String>,
    val substring: Expression<String>,
    val replacement: Expression<String>,
) : Expression<String>
