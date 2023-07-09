package com.linecorp.kotlinjdsl.module.jpql

import com.linecorp.kotlinjdsl.render.jpql.introspector.impl.JakartaJpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer

internal class DefaultJpqlModule : JpqlModule {
    private val introspector = JakartaJpqlIntrospector()

    private val serializers: List<JpqlSerializer<*>> = listOf(

    )

    override fun setupModule(context: JpqlModule.SetupContext) {
        context.prependIntrospector(introspector)
        context.addAllSerializer(serializers)
    }
}
