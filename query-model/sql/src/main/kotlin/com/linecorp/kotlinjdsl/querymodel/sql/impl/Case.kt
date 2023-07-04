package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression

@Internal
data class Case<T>(
    val whens: Collection<CaseWhen<T>>,
    val `else`: Expression<out T>?,
) : Expression<T>
