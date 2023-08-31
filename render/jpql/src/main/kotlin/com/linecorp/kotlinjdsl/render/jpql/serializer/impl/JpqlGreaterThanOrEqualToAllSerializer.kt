package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThanOrEqualToAll
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlGreaterThanOrEqualToAllSerializer : JpqlSerializer<JpqlGreaterThanOrEqualToAll<*>> {
    override fun handledType(): KClass<JpqlGreaterThanOrEqualToAll<*>> {
        return JpqlGreaterThanOrEqualToAll::class
    }

    override fun serialize(part: JpqlGreaterThanOrEqualToAll<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.write(" ")
        writer.write(">=")
        writer.write(" ")

        writer.write("ALL")
        writer.write(" ")
        writer.writeParentheses {
            delegate.serialize(part.subquery, writer, context)
        }
    }
}
