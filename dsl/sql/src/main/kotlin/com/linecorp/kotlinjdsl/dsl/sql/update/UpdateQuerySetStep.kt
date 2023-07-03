package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.query.sql.Column
import com.linecorp.kotlinjdsl.query.sql.Expression

interface UpdateQuerySetStep<T : Any> {
    fun <V> set(column: Column<T, V>, expression: Expression<V>): UpdateQuerySetMoreStep<T>
}
