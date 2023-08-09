package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNot
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlNotSerializer : JpqlSerializer<JpqlNot> {
    override fun handledType(): KClass<JpqlNot> {
        return JpqlNot::class
    }

    override fun serialize(part: JpqlNot, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("NOT")
        writer.write("(")

        delegate.serialize(part.predicate, writer, context)

        writer.write(")")
    }
}
