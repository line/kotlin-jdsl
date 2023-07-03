package com.linecorp.kotlinjdsl.module.sql

import com.linecorp.kotlinjdsl.module.sql.introspector.SqlIntrospectorModifier
import com.linecorp.kotlinjdsl.module.sql.serializer.SqlSerializerModifier
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer

interface SqlModule {
    fun setupModule(context: SetupContext)

    interface SetupContext {
        fun addSerializer(serializer: SqlSerializer<*>)
        fun addAllSerializer(vararg serializers: SqlSerializer<*>)
        fun addAllSerializer(serializers: Iterable<SqlSerializer<*>>)

        fun prependSerializerModifier(serializerModifier: SqlSerializerModifier)
        fun appendSerializerModifier(serializerModifier: SqlSerializerModifier)

        fun prependIntrospector(introspector: SqlIntrospector)
        fun appendIntrospector(introspector: SqlIntrospector)

        fun prependIntrospectorModifier(introspectorModifier: SqlIntrospectorModifier)
        fun appendIntrospectorModifier(introspectorModifier: SqlIntrospectorModifier)
    }
}
