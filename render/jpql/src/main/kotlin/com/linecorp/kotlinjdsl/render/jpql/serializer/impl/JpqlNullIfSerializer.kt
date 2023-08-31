package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNullIf
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlNullIfSerializer : JpqlSerializer<JpqlNullIf<*>> {
    override fun handledType(): KClass<JpqlNullIf<*>> {
        return JpqlNullIf::class
    }

    override fun serialize(part: JpqlNullIf<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("NULLIF")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)

            writer.write(",")
            writer.write(" ")

            delegate.serialize(part.compareValue, writer, context)
        }
    }
}
