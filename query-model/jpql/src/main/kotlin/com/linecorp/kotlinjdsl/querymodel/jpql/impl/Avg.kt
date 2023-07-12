package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class Avg(
    val expression: Expression<out Number?>,
    val distinct: Boolean,
) : Expression<Double?>
