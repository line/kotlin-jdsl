package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubstring
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSubstringSerializer : JpqlSerializer<JpqlSubstring> {
    override fun handledType(): KClass<JpqlSubstring> {
        return JpqlSubstring::class
    }

    override fun serialize(part: JpqlSubstring, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("SUBSTRING")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)

            writer.write(",")
            writer.write(" ")

            delegate.serialize(part.start, writer, context)

            part.length?.let {
                writer.write(",")
                writer.write(" ")

                delegate.serialize(it, writer, context)
            }
        }
    }
}
