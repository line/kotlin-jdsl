package com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlDeleteQuery<T : Any> internal constructor(
    val entity: Entity<T>,
    val where: Predicate?,
) : DeleteQuery<T>
