package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCoalesce
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
internal class JpqlCoalesceSerializer : JpqlSerializer<JpqlCoalesce<*>> {
    override fun handledType(): KClass<JpqlCoalesce<*>> {
        return JpqlCoalesce::class
    }

    override fun serialize(part: JpqlCoalesce<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("COALESCE")

        writer.writeParentheses {
            writer.writeEach(part.expr, separator = ", ") {
                delegate.serialize(it, writer, context)
            }
        }
    }
}
