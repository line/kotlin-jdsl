package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

/**
 * A subquery is a [SelectQuery] within another [SelectQuery].
 */
@Internal
data class JpqlSubquery<T : Any> internal constructor(
    val selectQuery: SelectQuery<T>,
) : Subquery<T>
