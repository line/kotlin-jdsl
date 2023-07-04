package com.linecorp.kotlinjdsl.querymodel.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
data class TableReference<T : Any>(
    val type: KClass<T>,
) : Table<T>
