package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.querymodel.sql.Expression

interface UpdateQuerySetStep<T : Any> {
    fun <V> set(
        column: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V>,
        expression: Expression<V>
    ): UpdateQuerySetMoreStep<T>
}
