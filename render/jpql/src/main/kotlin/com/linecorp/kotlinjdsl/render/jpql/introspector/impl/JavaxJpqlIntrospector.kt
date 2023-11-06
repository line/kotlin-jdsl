package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlPropertyDescription
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

/**
 * Introspector that introspects KClass and KCallable using [javax.persistence.Entity].
 */
@Internal
class JavaxJpqlIntrospector : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        val entity = type.findAnnotations(javax.persistence.Entity::class).firstOrNull()

        return if (entity != null) {
            JavaxEntity(entity.name.takeIf { it.isNotBlank() } ?: type.simpleName!!)
        } else {
            null
        }
    }

    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        TODO("Not yet implemented")
    }
}

private data class JavaxEntity(
    override val name: String,
) : JpqlEntityDescription
