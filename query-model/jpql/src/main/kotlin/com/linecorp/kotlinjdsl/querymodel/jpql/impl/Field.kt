package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path

data class Field<T>(
    val owner: Path<*>,
    val path: String,
) : Path<T>
