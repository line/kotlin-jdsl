package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface SelectQuery<T> : JpqlQuery<SelectQuery<T>> {
    val returnType: KClass<*>
}
