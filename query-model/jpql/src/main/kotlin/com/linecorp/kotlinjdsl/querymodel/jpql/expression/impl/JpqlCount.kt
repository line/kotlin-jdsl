package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlCount internal constructor(
    val expression: Expression<*>,
    val distinct: Boolean,
) : Expression<Long>
