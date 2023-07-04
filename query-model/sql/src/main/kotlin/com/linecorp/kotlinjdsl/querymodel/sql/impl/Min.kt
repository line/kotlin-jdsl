package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression

@Internal
data class Min<T : Comparable<T>>(
    val expression: Expression<T>,
    val distinct: Boolean,
) : Expression<T>
