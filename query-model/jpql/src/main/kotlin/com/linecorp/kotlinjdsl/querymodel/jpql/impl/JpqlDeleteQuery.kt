package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

data class JpqlDeleteQuery<T>(
    val from: Path<T>,
    val where: Predicate,
) : DeleteQuery<T>
