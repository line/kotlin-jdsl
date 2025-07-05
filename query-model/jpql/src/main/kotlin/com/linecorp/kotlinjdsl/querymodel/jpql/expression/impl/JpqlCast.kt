package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

@SinceJdsl("3.6.0")
data class JpqlCast<T : Any> internal constructor(
    val value: Expression<*>,
    val type: KClass<T>,
) : Expression<T>
