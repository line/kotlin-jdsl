package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

data class Join<T>(
    val left: Path<*>,
    val right: Path<T>,
    val on: Predicate?,
    val joinType: JoinType,
    val fetch: Boolean,
) : Path<T>
