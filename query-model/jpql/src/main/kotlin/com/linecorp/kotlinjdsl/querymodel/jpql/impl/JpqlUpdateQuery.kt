package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.UpdateQuery

data class JpqlUpdateQuery<T>(
    val entity: Path<T>,
    val set: Map<Path<*>, Expression<*>>,
    val where: Predicate?,
) : UpdateQuery<T> {
}
