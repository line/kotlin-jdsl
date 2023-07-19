package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.*

@Internal
data class NormalSelectQuery(
    val select: Iterable<Expression<*>>,
    val distinct: Boolean,
    val from: Iterable<Table<*>>?,
    val where: Predicate?,
    val groupBy: Iterable<Expression<*>>?,
    val having: Predicate?,
    val orderBy: Iterable<Sort>?,
    val offset: Int?,
    val limit: Int?,
) : SelectQuery
