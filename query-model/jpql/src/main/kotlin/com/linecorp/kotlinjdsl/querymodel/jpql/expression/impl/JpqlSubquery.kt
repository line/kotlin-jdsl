package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

@Internal
data class JpqlSubquery<T> internal constructor(
    val selectQuery: SelectQuery<T>,
) : Subquery<T>
