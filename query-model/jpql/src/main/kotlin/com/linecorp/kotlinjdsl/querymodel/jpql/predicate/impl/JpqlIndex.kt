package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

@Internal
data class JpqlIndex<T, S : Collection<T>>internal constructor(
    val path: Path<S>,
) : Expression<Int>
