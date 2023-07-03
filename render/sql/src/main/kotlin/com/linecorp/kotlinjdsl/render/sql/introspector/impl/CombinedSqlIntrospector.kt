package com.linecorp.kotlinjdsl.render.sql.introspector.impl

import com.linecorp.kotlinjdsl.render.sql.introspector.SqlColumnDescription
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlTableDescription
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class CombinedSqlIntrospector(
    private val primary: SqlIntrospector,
    private val secondary: SqlIntrospector,
) : SqlIntrospector {
    override fun introspect(clazz: KClass<*>): SqlTableDescription? {
        return primary.introspect(clazz) ?: secondary.introspect(clazz)
    }

    override fun introspect(property: KProperty1<*, *>): SqlColumnDescription? {
        return primary.introspect(property) ?: secondary.introspect(property)
    }
}
