package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPower
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlPowerSerializer : JpqlSerializer<JpqlPower<*>> {
    override fun handledType(): KClass<JpqlPower<*>> {
        return JpqlPower::class
    }

    override fun serialize(part: JpqlPower<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("POWER")

        writer.writeParentheses {
            delegate.serialize(part.base, writer, context)
        }
    }
}
