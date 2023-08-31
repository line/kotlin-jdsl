package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface JpqlIntrospector {
    @SinceJdsl("3.0.0")
    fun introspect(type: KClass<*>): JpqlEntityDescription?
}
