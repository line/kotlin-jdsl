package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAbs
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlAbsSerializer : JpqlSerializer<JpqlAbs<*>> {
    override fun handledType(): KClass<JpqlAbs<*>> {
        return JpqlAbs::class
    }

    override fun serialize(part: JpqlAbs<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("ABS")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
        }
    }
}
