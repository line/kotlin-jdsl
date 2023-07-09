package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path

data class MapValue<T, V>(
    val path: Path<Map<T, V>>,
) : Expression<V>
