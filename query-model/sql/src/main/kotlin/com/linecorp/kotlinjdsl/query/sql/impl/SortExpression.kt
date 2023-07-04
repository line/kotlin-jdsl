package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Sort

@Internal
data class SortExpression(
    val expression: Expression<*>,
    val order: Sort.Order,
) : Sort
