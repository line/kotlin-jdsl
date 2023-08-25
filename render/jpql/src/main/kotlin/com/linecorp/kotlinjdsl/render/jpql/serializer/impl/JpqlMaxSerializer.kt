package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMax
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlMaxSerializer : JpqlSerializer<JpqlMax<*>> {
    override fun handledType(): KClass<JpqlMax<*>> {
        return JpqlMax::class
    }

    override fun serialize(part: JpqlMax<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("MAX")

        writer.writeParentheses {
            if (part.distinct) {
                writer.write("DISTINCT")
                writer.write(" ")
            }

            delegate.serialize(part.expr, writer, context)
        }
    }
}
