package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlUpper
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlUpperSerializer : JpqlSerializer<JpqlUpper> {
    override fun handledType(): KClass<JpqlUpper> {
        return JpqlUpper::class
    }

    override fun serialize(part: JpqlUpper, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("UPPER")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
        }
    }
}
