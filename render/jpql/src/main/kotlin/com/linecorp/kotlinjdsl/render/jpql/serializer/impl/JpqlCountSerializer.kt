package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCount
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlCountSerializer : JpqlSerializer<JpqlCount> {
    override fun handledType(): KClass<JpqlCount> {
        return JpqlCount::class
    }

    override fun serialize(part: JpqlCount, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("COUNT")
        writer.write("(")

        if (part.distinct) {
            writer.write("DISTINCT")
            writer.write(" ")
        }

        delegate.serialize(part.expr, writer, context)

        writer.write(")")
    }
}
