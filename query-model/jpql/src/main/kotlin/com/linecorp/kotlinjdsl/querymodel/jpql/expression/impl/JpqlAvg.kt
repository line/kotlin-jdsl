package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

data class JpqlAvg internal constructor(
    val expression: Expression<out Number?>,
    val distinct: Boolean,
) : Expression<Double?>
