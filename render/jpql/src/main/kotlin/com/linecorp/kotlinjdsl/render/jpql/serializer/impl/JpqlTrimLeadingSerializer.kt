package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimLeading
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlTrimLeadingSerializer : JpqlSerializer<JpqlTrimLeading> {
    override fun handledType(): KClass<JpqlTrimLeading> {
        return JpqlTrimLeading::class
    }

    override fun serialize(part: JpqlTrimLeading, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("TRIM")

        writer.writeParentheses {
            writer.write("LEADING")
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
