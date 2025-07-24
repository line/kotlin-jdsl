package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlId
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlIdSerializer : JpqlSerializer<JpqlId<*>> {
    override fun handledType(): KClass<JpqlId<*>> {
        return JpqlId::class
    }

    override fun serialize(part: JpqlId<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("ID")

        writer.writeParentheses {
            delegate.serialize(part.expr.toExpression(), writer, context)
        }
    }
}
