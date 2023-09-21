package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@Internal
data class JpqlLocate internal constructor(
    val substring: Expression<String>,
    val string: Expression<String>,
    val start: Expression<Int>?,
) : Expression<Int>
