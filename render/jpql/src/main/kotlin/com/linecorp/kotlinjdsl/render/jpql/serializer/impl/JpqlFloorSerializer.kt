package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFloor
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlFloorSerializer : JpqlSerializer<JpqlFloor<*>> {
    override fun handledType(): KClass<JpqlFloor<*>> {
        return JpqlFloor::class
    }

    override fun serialize(part: JpqlFloor<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("FLOOR")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
        }
    }
}
