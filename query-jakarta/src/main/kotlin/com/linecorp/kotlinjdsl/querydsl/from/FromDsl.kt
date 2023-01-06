package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import kotlin.reflect.KClass

interface FromDsl {
    fun from(entity: Class<*>) = from(EntitySpec(entity))
    fun from(entity: KClass<*>) = from(EntitySpec(entity.java))
    fun from(entity: EntitySpec<*>)
}
