package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlReplace
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlReplaceSerializer : JpqlSerializer<JpqlReplace> {
    override fun handledType(): KClass<JpqlReplace> {
        return JpqlReplace::class
    }

    override fun serialize(part: JpqlReplace, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("REPLACE")
        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
            writer.write(", ")
            delegate.serialize(part.substring, writer, context)
            writer.write(", ")
            delegate.serialize(part.replacement, writer, context)
        }
    }
}
