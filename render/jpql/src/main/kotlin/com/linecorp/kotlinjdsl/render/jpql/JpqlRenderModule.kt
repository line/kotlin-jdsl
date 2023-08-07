package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospectorModifier
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerModifier

interface JpqlRenderModule {
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
