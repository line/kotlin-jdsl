package com.linecorp.kotlinjdsl.module.jpql.introspector

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector

interface JpqlIntrospectorModifier {
    fun modifyIntrospector(introspector: JpqlIntrospector): JpqlIntrospector
}
