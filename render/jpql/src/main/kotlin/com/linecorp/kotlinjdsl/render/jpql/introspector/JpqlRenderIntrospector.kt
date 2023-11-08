package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

/**
 * JpqlIntrospector that can be used in the [RenderContext].
 */
@SinceJdsl("3.0.0")
class JpqlRenderIntrospector(
    private val introspector: JpqlIntrospector,
) : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderIntrospector>

    private val classTableLookupCache: MutableMap<KClass<*>, JpqlEntityDescription> = ConcurrentHashMap()
    private val propertyTableLookupCache: MutableMap<KCallable<*>, JpqlPropertyDescription> = ConcurrentHashMap()

    /**
     * Creates a new introspector by combining this introspector and the introspector.
     */
    @SinceJdsl("3.0.0")
    operator fun plus(introspector: JpqlIntrospector): JpqlRenderIntrospector {
        val combinedIntrospector = CombinedJpqlIntrospector(
            primary = this.introspector,
            secondary = introspector,
        )

        return JpqlRenderIntrospector(combinedIntrospector)
    }

    /**
     * Introspects the KClass to get the entity information.
     */
    @SinceJdsl("3.0.0")
    fun introspect(clazz: KClass<*>): JpqlEntityDescription {
        return getCachedDescription(clazz)
    }

    /**
     * Introspects the KCallable to get the property information.
     */
    @SinceJdsl("3.1.0")
    fun introspect(property: KCallable<*>): JpqlPropertyDescription {
        return getCachedDescription(property)
    }

    private fun getCachedDescription(clazz: KClass<*>): JpqlEntityDescription {
        return classTableLookupCache.computeIfAbsent(clazz) {
            getDescription(it)
        }
    }

    private fun getCachedDescription(property: KCallable<*>): JpqlPropertyDescription {
        return propertyTableLookupCache.computeIfAbsent(property) {
            getDescription(it)
        }
    }

    private fun getDescription(clazz: KClass<*>): JpqlEntityDescription {
        return introspector.introspect(clazz)
            ?: throw IllegalStateException("There is no description for ${clazz.java.name}")
    }

    private fun getDescription(property: KCallable<*>): JpqlPropertyDescription {
        return introspector.introspect(property)
            ?: throw IllegalStateException("There is no description for ${property.name}")
    }
}
