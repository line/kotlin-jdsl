package com.linecorp.kotlinjdsl.querymodel.jpql.select.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Subquery

data class JpqlSubquery<T> internal constructor(
    val selectQuery: SelectQuery<T>,
) : Subquery<T>
