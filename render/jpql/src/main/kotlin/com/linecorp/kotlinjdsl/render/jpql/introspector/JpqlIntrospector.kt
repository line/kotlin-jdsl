package com.linecorp.kotlinjdsl.render.jpql.introspector

import kotlin.reflect.KClass

interface JpqlIntrospector {
    fun introspect(type: KClass<*>): JpqlEntityDescription?
}
