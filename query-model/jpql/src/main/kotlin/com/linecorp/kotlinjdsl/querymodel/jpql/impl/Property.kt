package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path

data class Property<T, V>(
    val owner: Path<T>,
    val path: String,
) : Path<V>
