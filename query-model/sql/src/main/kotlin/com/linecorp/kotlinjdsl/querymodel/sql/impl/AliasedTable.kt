package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Table

@Internal
data class AliasedTable<T : Any>(
    val table: Table<T>,
    val alias: String,
) : Table<T>
