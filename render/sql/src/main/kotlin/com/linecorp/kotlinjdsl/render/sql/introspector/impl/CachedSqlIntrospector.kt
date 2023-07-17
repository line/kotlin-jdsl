package com.linecorp.kotlinjdsl.render.sql.introspector.impl

import com.linecorp.kotlinjdsl.render.sql.introspector.SqlColumnDescription
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlTableDescription
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class CachedSqlIntrospector(private val delegate: SqlIntrospector) : SqlIntrospector {
    private val tableClasses: MutableMap<KClass<*>, SqlTableDescription> = ConcurrentHashMap()
    private val columnProperties: MutableMap<KProperty1<*, *>, SqlColumnDescription> = ConcurrentHashMap()

    override fun introspect(clazz: KClass<*>): SqlTableDescription? {
        if (clazz !in tableClasses) {
            delegate.introspect(clazz)?.run { tableClasses[clazz] = this }
        }

        return tableClasses[clazz]
    }

    override fun introspect(property: KProperty1<*, *>): SqlColumnDescription? {
        if (property !in columnProperties) {
            delegate.introspect(property)?.run { columnProperties[property] = this }
        }

        return columnProperties[property]
    }
}
