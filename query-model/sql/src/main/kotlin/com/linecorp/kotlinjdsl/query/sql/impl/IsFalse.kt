package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Predicate

@Internal
data class IsFalse<T : Boolean?>(
    val expression: Expression<T>,
    val not: Boolean,
) : Predicate
