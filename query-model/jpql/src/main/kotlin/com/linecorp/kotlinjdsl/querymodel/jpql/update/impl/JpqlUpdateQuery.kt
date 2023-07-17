package com.linecorp.kotlinjdsl.querymodel.jpql.update.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

data class JpqlUpdateQuery<T> internal constructor(
    val entity: Path<T>,
    val set: Map<Path<*>, Expression<*>>,
    val where: Predicate?,
) : UpdateQuery<T> {
}
