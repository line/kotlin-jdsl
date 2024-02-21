package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMod
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlModSerializer : JpqlSerializer<JpqlMod<*>> {
    override fun handledType(): KClass<JpqlMod<*>> {
        return JpqlMod::class
    }

    override fun serialize(part: JpqlMod<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("MOD")

        writer.writeParentheses {
            delegate.serialize(part.value1, writer, context)

            writer.write(",")
            writer.write(" ")

            delegate.serialize(part.value2, writer, context)
        }
    }
}
