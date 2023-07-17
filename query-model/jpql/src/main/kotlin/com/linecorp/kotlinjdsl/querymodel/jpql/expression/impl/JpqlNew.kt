package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

data class JpqlNew<T : Any> internal constructor(
    val type: KClass<T>,
    val args: Collection<Expression<*>>,
) : Expression<T>
