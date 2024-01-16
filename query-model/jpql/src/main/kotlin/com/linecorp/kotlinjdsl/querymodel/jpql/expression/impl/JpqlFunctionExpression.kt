package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

@Internal
data class JpqlFunctionExpression<T : Any> internal constructor(
    val type: KClass<T>,
    val name: String,
    val args: Iterable<Expression<*>>,
) : Expression<T>
