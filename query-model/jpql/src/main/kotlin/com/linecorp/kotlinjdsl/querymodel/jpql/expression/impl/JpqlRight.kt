package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@SinceJdsl("3.6.0")
data class JpqlRight internal constructor(
    val value: Expression<String>,
    val length: Expression<Int>,
) : Expression<String>
