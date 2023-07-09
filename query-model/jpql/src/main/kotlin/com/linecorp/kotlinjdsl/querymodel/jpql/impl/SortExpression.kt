package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Sort

data class SortExpression(
    val expression: Expression<*>,
    val order: Sort.Order,
) : Sort
