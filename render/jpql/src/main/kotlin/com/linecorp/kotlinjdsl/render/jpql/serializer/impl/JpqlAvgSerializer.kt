package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAvg
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlAvgSerializer : JpqlSerializer<JpqlAvg<*>> {
    override fun handledType(): KClass<JpqlAvg<*>> {
        return JpqlAvg::class
    }

    override fun serialize(part: JpqlAvg<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("AVG")

        writer.writeParentheses {
            if (part.distinct) {
                writer.write("DISTINCT")
                writer.write(" ")
            }

            delegate.serialize(part.expr, writer, context)
        }
    }
}
