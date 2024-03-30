package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIndex
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlIndexSerializer : JpqlSerializer<JpqlIndex> {
    override fun handledType(): KClass<JpqlIndex> {
        return JpqlIndex::class
    }

    override fun serialize(part: JpqlIndex, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("INDEX")

        writer.writeParentheses {
            delegate.serialize(part.entity, writer, context)
        }
    }
}
