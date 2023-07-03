package com.linecorp.kotlinjdsl.render.sql.introspector

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

interface SqlIntrospector {
    fun introspect(clazz: KClass<*>): SqlTableDescription?
    fun introspect(property: KProperty1<*, *>): SqlColumnDescription?
}
