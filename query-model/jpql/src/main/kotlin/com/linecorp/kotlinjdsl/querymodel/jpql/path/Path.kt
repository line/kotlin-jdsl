package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface Path<T> : Expression<T> {
    val type: KClass<*>
}
