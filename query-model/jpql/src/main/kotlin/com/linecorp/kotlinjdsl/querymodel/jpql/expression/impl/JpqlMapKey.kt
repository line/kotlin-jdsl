package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

data class JpqlMapKey<T, V> internal constructor(
    val path: Path<Map<T, V>>,
) : Expression<T>
