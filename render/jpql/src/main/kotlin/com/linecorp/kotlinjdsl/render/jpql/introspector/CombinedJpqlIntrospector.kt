package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KCallable
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

    /**
     * Get the entity information by introspecting KCallable.
     *
     * If the primary introspector introspects this KCallable, it returns the result of the primary introspector.
     * Otherwise, it returns the result of the secondary introspector.
     */
    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        return primary.introspect(property) ?: secondary.introspect(property)
    }
}
