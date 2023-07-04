package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.SelectQuery
import com.linecorp.kotlinjdsl.query.sql.Table

@Internal
data class DerivedTable<T : Any>(
    val select: SelectQuery
) : Table<T>
