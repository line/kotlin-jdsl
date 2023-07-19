package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.*

@Internal
data class NormalInsertQuery<T : Any>(
    val table: TableReference<T>,
    val columns: Iterable<Column<T, *>>?,
    val values: Iterable<Iterable<Expression<*>>>?,
    val select: Table<*>?,
) : InsertQuery<T>
