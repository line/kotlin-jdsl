package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression

@Internal
data class Sum<T : Number, R : Number>(
    val expression: Expression<T>,
    val distinct: Boolean,
) : Expression<R>
