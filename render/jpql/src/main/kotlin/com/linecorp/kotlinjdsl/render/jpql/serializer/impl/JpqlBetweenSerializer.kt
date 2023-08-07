package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlBetween
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlBetweenSerializer : JpqlSerializer<JpqlBetween> {
    override fun handledType(): KClass<JpqlBetween> {
        return JpqlBetween::class
    }

    override fun serialize(part: JpqlBetween, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.writeIfAbsent(" ")
        writer.write("BETWEEN")
        writer.write(" ")

        delegate.serialize(part.min, writer, context)

        writer.writeIfAbsent(" ")
        writer.write("AND")
        writer.write(" ")

        delegate.serialize(part.max, writer, context)
    }
}
