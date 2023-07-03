package com.linecorp.kotlinjdsl.render.sql.introspector.impl

import com.linecorp.kotlinjdsl.render.sql.introspector.SqlColumnDescription
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlTableDescription
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class NameBasedSqlIntrospector : SqlIntrospector {
    override fun introspect(clazz: KClass<*>): SqlTableDescription {
        return object : SqlTableDescription {
            override val name: String = clazz.simpleName!!
        }
    }

    override fun introspect(property: KProperty1<*, *>): SqlColumnDescription {
        return object : SqlColumnDescription {
            override val name: String = property.name
        }
    }
}
