package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotBetween
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlNotBetweenSerializer : JpqlSerializer<JpqlNotBetween> {
    override fun handledType(): KClass<JpqlNotBetween> {
        return JpqlNotBetween::class
    }

    override fun serialize(part: JpqlNotBetween, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.write(" ")
        writer.write("NOT BETWEEN")
        writer.write(" ")

        delegate.serialize(part.min, writer, context)

        writer.write(" ")
        writer.write("AND")
        writer.write(" ")

        delegate.serialize(part.max, writer, context)
    }
}
