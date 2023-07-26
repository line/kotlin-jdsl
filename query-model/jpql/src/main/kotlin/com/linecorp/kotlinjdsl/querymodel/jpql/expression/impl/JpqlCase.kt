package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlCase<T : Any> internal constructor(
    val whens: Map<Predicate, Expression<T>>,
    val `else`: Expression<*>?,
) : Expression<T>
