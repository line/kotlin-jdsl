package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Interface to modify the introspector.
 */
@SinceJdsl("3.0.0")
interface JpqlIntrospectorModifier {
    /**
     * Returns the modified introspector.
     */
    @SinceJdsl("3.0.0")
    fun modifyIntrospector(introspector: JpqlIntrospector): JpqlIntrospector
}
