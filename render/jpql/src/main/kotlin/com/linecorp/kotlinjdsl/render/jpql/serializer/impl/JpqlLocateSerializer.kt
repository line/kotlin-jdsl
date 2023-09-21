package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocate
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLocateSerializer : JpqlSerializer<JpqlLocate> {
    override fun handledType(): KClass<JpqlLocate> {
        return JpqlLocate::class
    }

    override fun serialize(part: JpqlLocate, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("LOCATE")

        writer.writeParentheses {
            delegate.serialize(part.substring, writer, context)

            writer.write(",")
            writer.write(" ")

            delegate.serialize(part.string, writer, context)

            part.start?.let {
                writer.write(",")
                writer.write(" ")

                delegate.serialize(it, writer, context)
            }
        }
    }
}
