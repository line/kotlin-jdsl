package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import kotlin.reflect.KClass

class CombinedJpqlIntrospector(
    private val primary: JpqlIntrospector,
    private val secondary: JpqlIntrospector,
) : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        return primary.introspect(type) ?: secondary.introspect(type)
    }
}
