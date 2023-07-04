package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

@Internal
data class IsNull(
    val expression: Expression<*>,
    val not: Boolean,
) : Predicate {
}
