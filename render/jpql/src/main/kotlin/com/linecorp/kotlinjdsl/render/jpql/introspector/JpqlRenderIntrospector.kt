package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.impl.CombinedJpqlIntrospector
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class JpqlRenderIntrospector(
    private val introspector: JpqlIntrospector,
) : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderIntrospector>

    private val tableLookupCache: MutableMap<KClass<*>, JpqlEntityDescription> = ConcurrentHashMap()

    operator fun plus(introspector: JpqlIntrospector): JpqlRenderIntrospector {
        val combinedIntrospector = CombinedJpqlIntrospector(
            primary = introspector,
            secondary = this.introspector,
        )

        return JpqlRenderIntrospector(combinedIntrospector)
    }

    fun introspect(clazz: KClass<*>): JpqlEntityDescription {
        return getCachedDescription(clazz)
    }

    private fun getCachedDescription(clazz: KClass<*>): JpqlEntityDescription {
        return tableLookupCache.computeIfAbsent(clazz) {
            getDescription(it)
        }
    }

    private fun getDescription(clazz: KClass<*>): JpqlEntityDescription {
        return introspector.introspect(clazz)
            ?: throw IllegalStateException("There is no description for ${clazz.java.name}")
    }
}
