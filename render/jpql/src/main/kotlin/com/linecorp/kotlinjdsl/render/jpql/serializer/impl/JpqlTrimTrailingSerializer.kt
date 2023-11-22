package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimTrailing
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlTrimTrailingSerializer : JpqlSerializer<JpqlTrimTrailing> {
    override fun handledType(): KClass<JpqlTrimTrailing> {
        return JpqlTrimTrailing::class
    }

    override fun serialize(part: JpqlTrimTrailing, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("TRIM")

        writer.writeParentheses {
            writer.write("TRAILING")
            writer.write(" ")

            part.character?.let {
                delegate.serialize(it, writer, context)
                writer.write(" ")
            }

            writer.write("FROM")
            writer.write(" ")

            delegate.serialize(part.value, writer, context)
        }
    }
}
