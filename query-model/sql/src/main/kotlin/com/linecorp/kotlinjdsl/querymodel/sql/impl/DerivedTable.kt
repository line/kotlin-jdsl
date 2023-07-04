package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.Table

@Internal
data class DerivedTable<T : Any>(
    val select: SelectQuery
) : Table<T>
