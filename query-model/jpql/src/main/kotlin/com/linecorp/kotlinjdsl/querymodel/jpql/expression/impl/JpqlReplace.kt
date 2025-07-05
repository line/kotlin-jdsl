package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@SinceJdsl("3.6.0")
data class JpqlReplace internal constructor(
    val value: Expression<String>,
    val pattern: Expression<String>,
    val replacement: Expression<String>,
) : Expression<String>
