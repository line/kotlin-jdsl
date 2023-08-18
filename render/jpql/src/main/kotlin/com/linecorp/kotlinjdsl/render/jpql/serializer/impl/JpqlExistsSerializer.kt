package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlExists
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlExistsSerializer : JpqlSerializer<JpqlExists> {
    override fun handledType(): KClass<JpqlExists> {
        return JpqlExists::class
    }

    override fun serialize(part: JpqlExists, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("EXISTS")
        writer.write(" ")
        writer.write("(")

        delegate.serialize(part.subquery, writer, context)

        writer.write(")")
    }
}
