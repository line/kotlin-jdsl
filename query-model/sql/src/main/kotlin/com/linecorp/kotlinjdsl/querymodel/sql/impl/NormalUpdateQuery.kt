package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.*

@Internal
data class NormalUpdateQuery<T : Any>(
    val table: TableReference<T>,
    val sets: Map<Column<T, *>, Expression<*>>,
    val where: Predicate?,
    val orderBy: Iterable<Expression<*>>?,
) : UpdateQuery<T>
