package com.linecorp.kotlinjdsl.render.sql.serializer

import com.linecorp.kotlinjdsl.SuperClassDepthComparator
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import java.util.concurrent.ConcurrentHashMap

class SqlRenderSerializer private constructor(
    private val mappedSerializers: Map<Class<*>, SqlSerializer<*>>,
) : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<SqlRenderSerializer>

    private val lookupCache: MutableMap<Class<*>, SqlSerializer<*>> = ConcurrentHashMap(mappedSerializers)

    constructor(vararg serializers: SqlSerializer<*>) : this(serializers.toList())
    constructor(serializers: Collection<SqlSerializer<*>>) : this(serializers.associateBy { it.handledType().java })

    operator fun plus(serializer: SqlSerializer<*>): SqlRenderSerializer {
        val combinedMappedSerializers = mappedSerializers + Pair(serializer.handledType().java, serializer)

        return SqlRenderSerializer(combinedMappedSerializers)
    }

    fun serialize(part: QueryPart, writer: SqlWriter, context: RenderContext) {
        @Suppress("UNCHECKED_CAST")
        val serializer = getCachedSerializer(part::class.java) as SqlSerializer<QueryPart>

        serializer.serialize(part, writer, context)
    }

    private fun getCachedSerializer(clazz: Class<*>): SqlSerializer<*> {
        return lookupCache.computeIfAbsent(clazz) {
            getSerializer(clazz)
        }
    }

    private fun getSerializer(clazz: Class<*>): SqlSerializer<*> {
        val matches = mappedSerializers.keys.filter { it.isAssignableFrom(clazz) }

        return if (matches.isNotEmpty()) {
            val sortedMatches = matches.sortedWith(SuperClassDepthComparator(clazz))

            mappedSerializers.getValue(sortedMatches.first())
        } else {
            throw IllegalStateException("There is no serializer for $clazz.")
        }
    }
}
