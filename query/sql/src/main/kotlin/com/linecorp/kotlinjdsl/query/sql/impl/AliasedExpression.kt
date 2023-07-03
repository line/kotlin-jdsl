package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression

@Internal
data class AliasedExpression<T>(
    val expression: Expression<T>,
    val alias: String,
) : Expression<T>
