package com.linecorp.kotlinjdsl.render.jpql.introspector

interface JpqlIntrospectorModifier {
    fun modifyIntrospector(introspector: JpqlIntrospector): JpqlIntrospector
}
