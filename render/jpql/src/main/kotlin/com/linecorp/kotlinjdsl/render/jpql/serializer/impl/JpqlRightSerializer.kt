package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlRight
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@SinceJdsl("3.6.0")
class JpqlRightSerializer : JpqlSerializer<JpqlRight> {
    override fun handledType(): KClass<JpqlRight> {
        return JpqlRight::class
    }

    override fun serialize(part: JpqlRight, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("RIGHT")
        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
            writer.write(", ")
            delegate.serialize(part.length, writer, context)
        }
    }
}
