package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlRound
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlRoundSerializer : JpqlSerializer<JpqlRound<*>>{
    override fun handledType(): KClass<JpqlRound<*>> {
        return JpqlRound::class
    }

    override fun serialize(part: JpqlRound<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("ROUND")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)

            writer.write(", ")
            delegate.serialize(part.scale, writer, context)
        }
    }
}
