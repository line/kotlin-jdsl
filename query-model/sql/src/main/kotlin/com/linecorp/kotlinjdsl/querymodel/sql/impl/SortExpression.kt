package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Sort

@Internal
data class SortExpression(
    val expression: Expression<*>,
    val order: Sort.Order,
) : Sort
