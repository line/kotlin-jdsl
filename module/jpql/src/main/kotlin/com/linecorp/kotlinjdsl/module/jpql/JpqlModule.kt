package com.linecorp.kotlinjdsl.module.jpql

import com.linecorp.kotlinjdsl.module.jpql.introspector.JpqlIntrospectorModifier
import com.linecorp.kotlinjdsl.module.jpql.serializer.JpqlSerializerModifier
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer

interface JpqlModule {
    fun setupModule(context: SetupContext)

    interface SetupContext {
        fun addSerializer(serializer: JpqlSerializer<*>)
        fun addAllSerializer(vararg serializers: JpqlSerializer<*>)
        fun addAllSerializer(serializers: Iterable<JpqlSerializer<*>>)

        fun prependSerializerModifier(serializerModifier: JpqlSerializerModifier)
        fun appendSerializerModifier(serializerModifier: JpqlSerializerModifier)

        fun prependIntrospector(introspector: JpqlIntrospector)
        fun appendIntrospector(introspector: JpqlIntrospector)

        fun prependIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier)
        fun appendIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier)
    }
}
