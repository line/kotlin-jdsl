package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression

@Internal
data class Min<T : Comparable<T>>(
    val expression: Expression<T>,
    val distinct: Boolean,
) : Expression<T>
