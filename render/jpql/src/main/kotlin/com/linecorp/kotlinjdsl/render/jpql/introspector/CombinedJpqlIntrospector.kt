package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

/**
 * [JpqlIntrospector] that combines two introspectors.
 */
@SinceJdsl("3.0.0")
class CombinedJpqlIntrospector(
    private val primary: JpqlIntrospector,
    private val secondary: JpqlIntrospector,
) : JpqlIntrospector {
    /**
     * Get the entity information by introspecting KClass.
     *
     * If the primary introspector introspects this KClass, it returns the result of the primary introspector.
     * Otherwise, it returns the result of the secondary introspector.
     */
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        return primary.introspect(type) ?: secondary.introspect(type)
    }
}
