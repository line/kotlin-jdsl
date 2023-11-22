package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrim
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlTrimSerializer : JpqlSerializer<JpqlTrim> {
    override fun handledType(): KClass<JpqlTrim> {
        return JpqlTrim::class
    }

    override fun serialize(part: JpqlTrim, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("TRIM")

        writer.writeParentheses {
            part.character?.let {
                delegate.serialize(it, writer, context)
                writer.write(" ")
                writer.write("FROM")
                writer.write(" ")
            }

            delegate.serialize(part.value, writer, context)
        }
    }
}
