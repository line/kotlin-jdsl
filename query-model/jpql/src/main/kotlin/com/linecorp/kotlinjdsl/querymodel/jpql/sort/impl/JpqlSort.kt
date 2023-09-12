package com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort

@Internal
data class JpqlSort internal constructor(
    val expr: Expression<*>,
    val order: Sort.Order,
    val nullOrder: Sort.NullOrder?,
) : Sort
