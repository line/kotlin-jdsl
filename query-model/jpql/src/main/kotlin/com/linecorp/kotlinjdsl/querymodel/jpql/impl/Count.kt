package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class Count(
    val expression: Expression<*>,
    val distinct: Boolean,
) : Expression<Long>
