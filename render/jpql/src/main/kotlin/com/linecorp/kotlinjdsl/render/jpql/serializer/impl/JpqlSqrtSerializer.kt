package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSqrt
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSqrtSerializer : JpqlSerializer<JpqlSqrt<*>> {
    override fun handledType(): KClass<JpqlSqrt<*>> {
        return JpqlSqrt::class
    }

    override fun serialize(part: JpqlSqrt<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("SQRT")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
        }
    }
}
