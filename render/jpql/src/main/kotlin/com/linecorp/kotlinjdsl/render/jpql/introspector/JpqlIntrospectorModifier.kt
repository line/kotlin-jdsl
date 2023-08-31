package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface JpqlIntrospectorModifier {
    @SinceJdsl("3.0.0")
    fun modifyIntrospector(introspector: JpqlIntrospector): JpqlIntrospector
}
