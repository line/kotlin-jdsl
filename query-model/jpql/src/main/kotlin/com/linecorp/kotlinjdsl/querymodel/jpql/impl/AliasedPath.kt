package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path

data class AliasedPath<T>(
    val path: Path<T>,
    val alias: String,
) : Path<T>
