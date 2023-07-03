package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.SelectQuery

@Internal
data class Subquery<T>(
    val select: SelectQuery,
) : Expression<T>
