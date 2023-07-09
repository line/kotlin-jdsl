package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

class JakartaJpqlIntrospector : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? {
        val entity = type.findAnnotations(jakarta.persistence.Entity::class).firstOrNull()

        return if (entity != null) {
            JakartaEntity(entity)
        } else {
            null
        }
    }
}

private data class JakartaEntity(
    val entity: jakarta.persistence.Entity,
) : JpqlEntityDescription {
    override val name: String = entity.name
}
