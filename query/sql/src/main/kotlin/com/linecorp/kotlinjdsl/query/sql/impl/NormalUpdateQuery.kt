package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.*

@Internal
data class NormalUpdateQuery<T : Any>(
    val table: TableReference<T>,
    val sets: Map<Column<T, *>, Expression<*>>,
    val where: Predicate?,
    val orderBy: Collection<Expression<*>>?,
) : UpdateQuery<T>
