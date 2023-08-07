package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

class JavaxJpqlIntrospector : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        val entity = type.findAnnotations(javax.persistence.Entity::class).firstOrNull()

        return if (entity != null) {
            JavaxEntity(entity.name.takeIf { it.isNotBlank() } ?: type.simpleName!!)
        } else {
            null
        }
    }
}

private data class JavaxEntity(
    override val name: String,
) : JpqlEntityDescription
