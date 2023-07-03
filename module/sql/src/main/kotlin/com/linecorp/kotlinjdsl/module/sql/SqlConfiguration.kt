package com.linecorp.kotlinjdsl.module.sql

import com.linecorp.kotlinjdsl.module.sql.introspector.SqlIntrospectorModifier
import com.linecorp.kotlinjdsl.module.sql.serializer.SqlSerializerModifier
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.sql.introspector.impl.CombinedSqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer

class SqlConfiguration {
    private val introspectors: MutableList<SqlIntrospector> = mutableListOf()
    private val introspectorModifiers: MutableList<SqlIntrospectorModifier> = mutableListOf()

    private val serializers: MutableList<SqlSerializer<*>> = mutableListOf()
    private val serializerModifiers: MutableList<SqlSerializerModifier> = mutableListOf()

    init {
        registerModule(DefaultSqlModule())
    }

    fun registerModule(module: SqlModule) {
        val setupContext = object : SqlModule.SetupContext {
            override fun addSerializer(serializer: SqlSerializer<*>) {
                serializers.add(serializer)
            }

            override fun addAllSerializer(vararg serializers: SqlSerializer<*>) {
                this@SqlConfiguration.serializers.addAll(serializers)
            }

            override fun addAllSerializer(serializers: Iterable<SqlSerializer<*>>) {
                this@SqlConfiguration.serializers.addAll(serializers)
            }

            override fun prependSerializerModifier(serializerModifier: SqlSerializerModifier) {
                serializerModifiers.add(0, serializerModifier)
            }

            override fun appendSerializerModifier(serializerModifier: SqlSerializerModifier) {
                serializerModifiers.add(serializerModifier)
            }

            override fun prependIntrospector(introspector: SqlIntrospector) {
                introspectors.add(0, introspector)
            }

            override fun appendIntrospector(introspector: SqlIntrospector) {
                introspectors.add(introspector)
            }

            override fun prependIntrospectorModifier(introspectorModifier: SqlIntrospectorModifier) {
                introspectorModifiers.add(0, introspectorModifier)
            }

            override fun appendIntrospectorModifier(introspectorModifier: SqlIntrospectorModifier) {
                introspectorModifiers.add(introspectorModifier)
            }
        }

        module.setupModule(setupContext)
    }

    fun registerModules(vararg modules: SqlModule) {
        modules.forEach { registerModule(it) }
    }

    fun registerModules(modules: Iterable<SqlModule>) {
        modules.forEach { registerModule(it) }
    }

    fun getConfiguredContext(): RenderContext {
        val renderIntrospector = createRenderIntrospector()
        val renderSerializer = createRenderSerializer()

        return renderIntrospector + renderSerializer
    }

    private fun createRenderIntrospector(): SqlRenderIntrospector {
        var introspector = introspectors.reduce { acc, introspector ->
            CombinedSqlIntrospector(acc, introspector)
        }

        introspectorModifiers.forEach {
            introspector = it.modifyIntrospector(introspector)
        }

        return SqlRenderIntrospector(introspector)
    }

    private fun createRenderSerializer(): SqlRenderSerializer {
        val serializers = this.serializers.toMutableList()

        serializers.forEach { serializer ->
            serializerModifiers.forEach { modifier ->
                val modifiedSerializer = modifier.modifySerializer(serializer)

                serializers.add(modifiedSerializer)
            }
        }

        return SqlRenderSerializer(serializers)
    }
}
