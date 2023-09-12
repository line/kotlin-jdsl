package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospectorModifier
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerModifier

/**
 * Interface to modify the [JpqlRenderContext].
 * This interface is used to register serializers and introspectors to [JpqlRenderContext].
 *
 * @see JpqlRenderContext
 */
@SinceJdsl("3.0.0")
interface JpqlRenderModule {
    /**
     * Setup serializers and introspectors of [JpqlRenderContext].
     * The serializers and introspectors can be registered in [JpqlRenderContext] by modifying the [SetupContext].
     */
    @SinceJdsl("3.0.0")
    fun setupModule(context: SetupContext)

    /**
     * Context to modify the [JpqlRenderContext].
     */
    @SinceJdsl("3.0.0")
    interface SetupContext {
        /**
         * Add a serializer to [JpqlRenderContext].
         * If there is more than one serializer handling the same type, the last serializer overrides the others.
         */
        @SinceJdsl("3.0.0")
        fun addSerializer(serializer: JpqlSerializer<*>)

        /**
         * Add serializers to [JpqlRenderContext].
         * If there is more than one serializer handling the same type, the last serializer overrides the others.
         */
        @SinceJdsl("3.0.0")
        fun addAllSerializer(vararg serializers: JpqlSerializer<*>)

        /**
         * Add serializers to [JpqlRenderContext].
         * If there is more than one serializer handling the same type, the last serializer overrides the others.
         */
        @SinceJdsl("3.0.0")
        fun addAllSerializer(serializers: Iterable<JpqlSerializer<*>>)

        /**
         * Add a serializer modifier to [JpqlRenderContext] as highest priority.
         */
        @SinceJdsl("3.0.0")
        fun prependSerializerModifier(serializerModifier: JpqlSerializerModifier)

        /**
         * Add a serializer modifier to [JpqlRenderContext] as lowest priority.
         */
        @SinceJdsl("3.0.0")
        fun appendSerializerModifier(serializerModifier: JpqlSerializerModifier)

        /**
         * Add an introspector to [JpqlRenderContext] as highest priority.
         */
        @SinceJdsl("3.0.0")
        fun prependIntrospector(introspector: JpqlIntrospector)

        /**
         * Add an introspector to [JpqlRenderContext] as lowest priority.
         */
        @SinceJdsl("3.0.0")
        fun appendIntrospector(introspector: JpqlIntrospector)

        /**
         * Add an introspector modifier to [JpqlRenderContext] as highest priority.
         */
        @SinceJdsl("3.0.0")
        fun prependIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier)

        /**
         * Add an introspector modifier to [JpqlRenderContext] as lowest priority.
         */
        @SinceJdsl("3.0.0")
        fun appendIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier)
    }
}
