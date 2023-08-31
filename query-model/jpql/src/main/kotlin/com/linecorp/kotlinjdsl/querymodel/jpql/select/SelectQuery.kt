package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface SelectQuery<T : Any> : JpqlQuery<SelectQuery<T>> {
    @SinceJdsl("3.0.0")
    val returnType: KClass<T>
}
