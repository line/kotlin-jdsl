package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotExists
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlNotExistsSerializer : JpqlSerializer<JpqlNotExists> {
    override fun handledType(): KClass<JpqlNotExists> {
        return JpqlNotExists::class
    }

    override fun serialize(part: JpqlNotExists, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("NOT EXISTS")
        writer.write(" ")

        writer.writeParentheses {
            delegate.serialize(part.subquery, writer, context)
        }
    }
}
