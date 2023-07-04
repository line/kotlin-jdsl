package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery

@Internal
data class Subquery<T>(
    val select: SelectQuery,
) : Expression<T>
