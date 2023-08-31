package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSum
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSumSerializer : JpqlSerializer<JpqlSum<*, *>> {
    override fun handledType(): KClass<JpqlSum<*, *>> {
        return JpqlSum::class
    }

    override fun serialize(part: JpqlSum<*, *>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("SUM")

        writer.writeParentheses {
            if (part.distinct) {
                writer.write("DISTINCT")
                writer.write(" ")
            }

            delegate.serialize(part.expr, writer, context)
        }
    }
}
