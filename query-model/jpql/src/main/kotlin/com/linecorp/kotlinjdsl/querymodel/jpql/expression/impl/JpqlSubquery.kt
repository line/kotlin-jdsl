package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

data class JpqlSubquery<T> internal constructor(
    val selectQuery: SelectQuery<T>,
) : Subquery<T>
