package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

@Internal
data class JpqlCast<T : Any> internal constructor(
    val value: Expression<*>,
    val type: KClass<T>,
) : Expression<T>
