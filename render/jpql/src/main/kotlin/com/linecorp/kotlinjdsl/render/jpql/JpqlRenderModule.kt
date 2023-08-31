package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospectorModifier
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerModifier

@SinceJdsl("3.0.0")
interface JpqlRenderModule {
    @SinceJdsl("3.0.0")
    fun setupModule(context: SetupContext)

    @SinceJdsl("3.0.0")
    interface SetupContext {
        @SinceJdsl("3.0.0")
        fun addSerializer(serializer: JpqlSerializer<*>)

        @SinceJdsl("3.0.0")
        fun addAllSerializer(vararg serializers: JpqlSerializer<*>)

        @SinceJdsl("3.0.0")
        fun addAllSerializer(serializers: Iterable<JpqlSerializer<*>>)

        @SinceJdsl("3.0.0")
        fun prependSerializerModifier(serializerModifier: JpqlSerializerModifier)

        @SinceJdsl("3.0.0")
        fun appendSerializerModifier(serializerModifier: JpqlSerializerModifier)

        @SinceJdsl("3.0.0")
        fun prependIntrospector(introspector: JpqlIntrospector)

        @SinceJdsl("3.0.0")
        fun appendIntrospector(introspector: JpqlIntrospector)

        @SinceJdsl("3.0.0")
        fun prependIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier)

        @SinceJdsl("3.0.0")
        fun appendIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier)
    }
}
