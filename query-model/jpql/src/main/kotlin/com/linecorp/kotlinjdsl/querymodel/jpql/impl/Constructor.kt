package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import kotlin.reflect.KClass

data class Constructor<T : Any>(
    val type: KClass<T>,
    val args: Collection<Expression<*>>,
) : Expression<T>
