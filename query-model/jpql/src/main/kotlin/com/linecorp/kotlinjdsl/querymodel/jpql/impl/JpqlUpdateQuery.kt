package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.UpdateQuery

data class JpqlUpdateQuery<T>(
    val entity: Path<T>,
    val sets: Map<Path<*>, Any?>,
    val where: Predicate,
) : UpdateQuery<T> {
}
