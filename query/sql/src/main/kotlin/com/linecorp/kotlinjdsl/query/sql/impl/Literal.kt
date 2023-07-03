package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression

@Internal
data class Literal<T>(
    val value: T
) : Expression<T>
