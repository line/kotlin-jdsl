package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl
import kotlin.reflect.KCallable

/**
 * Abstract class to get the entity information by introspecting KClass.
 */
@SinceJdsl("3.1.0")
abstract class JpqlEntityIntrospector : JpqlIntrospector {
    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? = null
}
