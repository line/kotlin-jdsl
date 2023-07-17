package com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort

data class JpqlSort internal constructor(
    val expression: Expression<*>,
    override val order: Sort.Order,
) : Sort
