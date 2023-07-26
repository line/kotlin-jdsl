package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery

/**
 * A subquery is a [SelectQuery] within another [SelectQuery].
 *
 * In JPA 2.1, It is restricted to the [JpqlSelectQuery.where] and [JpqlSelectQuery.having].
 */
@Internal
data class JpqlSubquery<T : Any> internal constructor(
    val selectQuery: SelectQuery<T>,
) : Subquery<T>
