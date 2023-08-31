package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
class CombinedJpqlIntrospector(
    private val primary: JpqlIntrospector,
    private val secondary: JpqlIntrospector,
) : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        return primary.introspect(type) ?: secondary.introspect(type)
    }
}
