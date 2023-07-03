package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Table

@Internal
data class TableAsterisk<T : Any>(
    val table: Table<T>,
) : Expression<T>
