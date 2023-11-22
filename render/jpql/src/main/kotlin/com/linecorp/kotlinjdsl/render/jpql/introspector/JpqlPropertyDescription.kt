package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Interface to represent the property information.
 */
@SinceJdsl("3.1.0")
interface JpqlPropertyDescription {
    @SinceJdsl("3.1.0")
    val name: String
}
