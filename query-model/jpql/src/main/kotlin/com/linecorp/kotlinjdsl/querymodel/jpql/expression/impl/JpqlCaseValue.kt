package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

@Internal
data class JpqlCaseValue<T : Any, V : Any> internal constructor(
    val value: Path<T>,
    val whens: Map<Expression<T>, Expression<V>>,
    val `else`: Expression<V>?,
) : Expression<V>
