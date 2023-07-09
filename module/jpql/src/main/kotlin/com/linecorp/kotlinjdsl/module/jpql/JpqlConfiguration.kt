package com.linecorp.kotlinjdsl.module.jpql

import com.linecorp.kotlinjdsl.module.jpql.introspector.JpqlIntrospectorModifier
import com.linecorp.kotlinjdsl.module.jpql.serializer.JpqlSerializerModifier
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.impl.CombinedJpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer

class JpqlConfiguration {
    private val introspectors: MutableList<JpqlIntrospector> = mutableListOf()
    private val introspectorModifiers: MutableList<JpqlIntrospectorModifier> = mutableListOf()

    private val serializers: MutableList<JpqlSerializer<*>> = mutableListOf()
    private val serializerModifiers: MutableList<JpqlSerializerModifier> = mutableListOf()

    init {
        registerModule(DefaultJpqlModule())
    }

    fun registerModule(module: JpqlModule) {
        val setupContext = object : JpqlModule.SetupContext {
            override fun addSerializer(serializer: JpqlSerializer<*>) {
                serializers.add(serializer)
            }

            override fun addAllSerializer(vararg serializers: JpqlSerializer<*>) {
                this@JpqlConfiguration.serializers.addAll(serializers)
            }

            override fun addAllSerializer(serializers: Iterable<JpqlSerializer<*>>) {
                this@JpqlConfiguration.serializers.addAll(serializers)
            }

            override fun prependSerializerModifier(serializerModifier: JpqlSerializerModifier) {
                serializerModifiers.add(0, serializerModifier)
            }

            override fun appendSerializerModifier(serializerModifier: JpqlSerializerModifier) {
                serializerModifiers.add(serializerModifier)
            }

            override fun prependIntrospector(introspector: JpqlIntrospector) {
                introspectors.add(0, introspector)
            }

            override fun appendIntrospector(introspector: JpqlIntrospector) {
                introspectors.add(introspector)
            }

            override fun prependIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier) {
                introspectorModifiers.add(0, introspectorModifier)
            }

            override fun appendIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier) {
                introspectorModifiers.add(introspectorModifier)
            }
        }

        module.setupModule(setupContext)
    }

    fun registerModules(vararg modules: JpqlModule) {
        modules.forEach { registerModule(it) }
    }

    fun registerModules(modules: Iterable<JpqlModule>) {
        modules.forEach { registerModule(it) }
    }

    fun getConfiguredContext(): RenderContext {
        val renderIntrospector = createRenderIntrospector()
        val renderSerializer = createRenderSerializer()

        return renderIntrospector + renderSerializer
    }

    private fun createRenderIntrospector(): JpqlRenderIntrospector {
        var introspector = introspectors.reduce { acc, introspector ->
            CombinedJpqlIntrospector(acc, introspector)
        }

        introspectorModifiers.forEach {
            introspector = it.modifyIntrospector(introspector)
        }

        return JpqlRenderIntrospector(introspector)
    }

    private fun createRenderSerializer(): JpqlRenderSerializer {
        val serializers = this.serializers.toMutableList()

        serializers.forEach { serializer ->
            serializerModifiers.forEach { modifier ->
                val modifiedSerializer = modifier.modifySerializer(serializer)

                serializers.add(modifiedSerializer)
            }
        }

        return JpqlRenderSerializer(serializers)
    }
}
