package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.Table

@Internal
data class CrossJoinedTable(
    val left: Table<*>,
    val right: Table<*>,
    val on: Predicate?,
) : Table<Any>
