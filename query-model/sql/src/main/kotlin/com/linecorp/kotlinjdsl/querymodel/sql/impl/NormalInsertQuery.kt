package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.Table
import com.linecorp.kotlinjdsl.querymodel.sql.TableReference

@Internal
data class NormalInsertQuery<T : Any>(
    val table: TableReference<T>,
    val columns: Collection<com.linecorp.kotlinjdsl.querymodel.sql.Column<T, *>>?,
    val values: Collection<Collection<Expression<*>>>?,
    val select: Table<*>?,
) : InsertQuery<T>
