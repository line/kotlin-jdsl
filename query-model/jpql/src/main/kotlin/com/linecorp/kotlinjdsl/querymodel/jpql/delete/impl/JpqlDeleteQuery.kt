package com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlDeleteQuery<T> internal constructor(
    val from: Path<T>,
    val where: Predicate?,
) : DeleteQuery<T>
