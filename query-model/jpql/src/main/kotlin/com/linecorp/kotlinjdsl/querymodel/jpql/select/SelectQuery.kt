package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface SelectQuery<T> : JpqlQuery<SelectQuery<T>> {
    val returnType: KClass<*>
}
