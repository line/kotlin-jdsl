package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlPropertyDescription
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotations

/**
 * Introspector that introspects KClass and KCallable using [jakarta.persistence.Entity].
 */
@Internal
class JakartaJpqlIntrospector : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        val entity = type.findAnnotations(jakarta.persistence.Entity::class).firstOrNull()

        return if (entity != null) {
            JakartaEntity(entity.name.takeIf { it.isNotBlank() } ?: type.simpleName!!)
        } else {
            null
        }
    }

    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        return when (property) {
            is KProperty1<*, *> -> JpqlProperty(property.name)
            is KFunction1<*, *> -> JpqlProperty(resolvePropertyName(property))
            else -> null
        }
    }

    private fun resolvePropertyName(getter: KFunction1<*, *>): String =
        if (getter.name.startsWith("is")) {
            getter.name
        } else {
            getter.name.removePrefix("get").replaceFirstChar { it.lowercase() }
        }
}

private data class JakartaEntity(
    override val name: String,
) : JpqlEntityDescription
