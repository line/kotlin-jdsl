package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

@Internal
data class JpqlCustomExpression<T : Any> internal constructor(
    val type: KClass<T>,
    val template: String,
    val args: Iterable<Expression<*>>,
) : Expression<T>
