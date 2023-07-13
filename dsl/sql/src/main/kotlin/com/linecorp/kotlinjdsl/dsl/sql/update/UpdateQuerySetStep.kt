package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.querymodel.sql.Column
import com.linecorp.kotlinjdsl.querymodel.sql.Expression

interface UpdateQuerySetStep<T : Any> {
    fun <V> set(
        column: Column<T, V>,
        expression: Expression<V>
    ): UpdateQuerySetMoreStep<T>
}
