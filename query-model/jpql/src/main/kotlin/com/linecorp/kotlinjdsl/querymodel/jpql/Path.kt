package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface Path<T> : SelectExpression, Expression<T> {
    val type: KClass<*>
}
