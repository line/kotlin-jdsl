package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.Table

@Internal
data class InnerJoinedTable(
    val left: Table<*>,
    val right: Table<*>,
    val on: Predicate?,
) : Table<Any>
