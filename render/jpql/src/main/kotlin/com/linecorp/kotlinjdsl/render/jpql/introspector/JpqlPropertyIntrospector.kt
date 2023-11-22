package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KClass

/**
 * Abstract class to get the entity information by introspecting KCallable.
 */
@SinceJdsl("3.1.0")
abstract class JpqlPropertyIntrospector : JpqlIntrospector {
    override fun introspect(type: KClass<*>): JpqlEntityDescription? = null
}
