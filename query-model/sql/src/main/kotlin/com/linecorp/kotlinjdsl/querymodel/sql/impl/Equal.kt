package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

@Internal
data class Equal<T>(
    val left: Expression<T>,
    val right: Expression<T>,
    val not: Boolean,
) : Predicate
