package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

@Internal
data class Between<T>(
    val left: Expression<T>,
    val right1: Expression<T>,
    val right2: Expression<T>,
    val not: Boolean,
) : Predicate
