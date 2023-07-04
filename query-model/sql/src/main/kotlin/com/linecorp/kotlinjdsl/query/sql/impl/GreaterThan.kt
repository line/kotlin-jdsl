package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Predicate

@Internal
data class GreaterThan<T : Comparable<T>?>(
    val left: Expression<T>,
    val right: Expression<T>,
    val inclusive: Boolean,
    val not: Boolean,
) : Predicate
