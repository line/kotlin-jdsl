package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.clazz.SuperClassDepthComparator
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import java.util.concurrent.ConcurrentHashMap

@SinceJdsl("3.0.0")
class JpqlRenderSerializer private constructor(
    private val mappedSerializers: Map<Class<*>, JpqlSerializer<*>>,
) : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderSerializer>

    private val lookupCache: MutableMap<Class<*>, JpqlSerializer<*>> = ConcurrentHashMap(mappedSerializers)

    @SinceJdsl("3.0.0")
    constructor(vararg serializers: JpqlSerializer<*>) : this(serializers.toList())

    @SinceJdsl("3.0.0")
    constructor(serializers: Iterable<JpqlSerializer<*>>) : this(serializers.associateBy { it.handledType().java })

    @SinceJdsl("3.0.0")
    operator fun plus(serializer: JpqlSerializer<*>): JpqlRenderSerializer {
        val combinedMappedSerializers = mappedSerializers + Pair(serializer.handledType().java, serializer)

        return JpqlRenderSerializer(combinedMappedSerializers)
    }

    @SinceJdsl("3.0.0")
    fun serialize(part: QueryPart, writer: JpqlWriter, context: RenderContext) {
        @Suppress("UNCHECKED_CAST")
        val serializer = getCachedSerializer(part::class.java) as JpqlSerializer<QueryPart>

        serializer.serialize(part, writer, context)
    }

    private fun getCachedSerializer(clazz: Class<*>): JpqlSerializer<*> {
        return lookupCache.computeIfAbsent(clazz) {
            getSerializer(clazz)
        }
    }

    private fun getSerializer(clazz: Class<*>): JpqlSerializer<*> {
        val matches = mappedSerializers.keys.filter { it.isAssignableFrom(clazz) }

        return if (matches.isNotEmpty()) {
            val sortedMatches = matches.sortedWith(SuperClassDepthComparator(clazz))

            mappedSerializers.getValue(sortedMatches.first())
        } else {
            throw IllegalStateException("There is no serializer for $clazz.")
        }
    }
}
