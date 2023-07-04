package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.*

@Internal
data class NormalInsertQuery<T : Any>(
    val table: TableReference<T>,
    val columns: Collection<Column<T, *>>?,
    val values: Collection<Collection<Expression<*>>>?,
    val select: Table<*>?,
) : InsertQuery<T>
