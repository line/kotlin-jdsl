package com.linecorp.kotlinjdsl.querymodel.jpql.update.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

@Internal
data class JpqlUpdateQuery<T : Any> internal constructor(
    val entity: Entity<T>,
    val set: Map<Path<*>, Expression<*>>,
    val where: Predicate?,
) : UpdateQuery<T>
