package com.linecorp.kotlinjdsl.querymodel.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KProperty1

@SinceJdsl("3.0.0")
data class Column<T : Any, V>(
    val table: com.linecorp.kotlinjdsl.querymodel.sql.Table<T>,
    val property: KProperty1<T, V>,
) : com.linecorp.kotlinjdsl.querymodel.sql.Expression<V>
