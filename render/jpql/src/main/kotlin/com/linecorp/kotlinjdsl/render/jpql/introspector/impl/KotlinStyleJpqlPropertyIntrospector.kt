package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlPropertyDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlPropertyIntrospector
import kotlin.reflect.KCallable
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

/**
 * Introspector that introspects a property name in KCallable using Kotlin style.
 */
@Internal
class KotlinStyleJpqlPropertyIntrospector : JpqlPropertyIntrospector() {
    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        return when (property) {
            is KProperty1<*, *> -> KotlinStyleProperty(property.name)
            is KFunction1<*, *> -> KotlinStyleProperty(resolvePropertyName(property))
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

private data class KotlinStyleProperty(
    override val name: String,
) : JpqlPropertyDescription
