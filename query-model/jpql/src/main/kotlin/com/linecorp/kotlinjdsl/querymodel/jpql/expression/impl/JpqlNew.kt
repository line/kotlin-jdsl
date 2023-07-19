package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

@Internal
data class JpqlNew<T : Any> internal constructor(
    val type: KClass<T>,
    val args: Iterable<Expression<*>>,
) : Expression<T>
