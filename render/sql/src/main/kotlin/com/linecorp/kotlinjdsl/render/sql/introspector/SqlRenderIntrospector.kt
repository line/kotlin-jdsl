package com.linecorp.kotlinjdsl.render.sql.introspector

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.introspector.impl.CombinedSqlIntrospector
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class SqlRenderIntrospector(
    private val introspector: SqlIntrospector,
) : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<SqlRenderIntrospector>

    private val tableLookupCache: MutableMap<KClass<*>, SqlTableDescription> = ConcurrentHashMap()
    private val columnLookupCache: MutableMap<KProperty1<*, *>, SqlColumnDescription> = ConcurrentHashMap()

    operator fun plus(introspector: SqlIntrospector): SqlRenderIntrospector {
        val combinedIntrospector = CombinedSqlIntrospector(
            primary = introspector,
            secondary = this.introspector,
        )

        return SqlRenderIntrospector(combinedIntrospector)
    }

    fun introspect(clazz: KClass<*>): SqlTableDescription {
        return getCachedDescription(clazz)
    }

    private fun getCachedDescription(clazz: KClass<*>): SqlTableDescription {
        return tableLookupCache.computeIfAbsent(clazz) {
            getDescription(it)
        }
    }

    private fun getDescription(clazz: KClass<*>): SqlTableDescription {
        return introspector.introspect(clazz)
            ?: throw IllegalStateException("There is no description for $clazz")
    }

    fun introspect(property: KProperty1<*, *>): SqlColumnDescription {
        return getCachedDescription(property)
    }

    private fun getCachedDescription(property: KProperty1<*, *>): SqlColumnDescription {
        return columnLookupCache.computeIfAbsent(property) {
            getDescription(property)
        }
    }

    private fun getDescription(property: KProperty1<*, *>): SqlColumnDescription {
        return introspector.introspect(property)
            ?: throw IllegalStateException("There is no description for $property")
    }
}
