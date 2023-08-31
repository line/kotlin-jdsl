package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanAny
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLessThanAnySerializer : JpqlSerializer<JpqlLessThanAny<*>> {
    override fun handledType(): KClass<JpqlLessThanAny<*>> {
        return JpqlLessThanAny::class
    }

    override fun serialize(part: JpqlLessThanAny<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.write(" ")
        writer.write("<")
        writer.write(" ")

        writer.write("ANY")
        writer.write(" ")
        writer.writeParentheses {
            delegate.serialize(part.subquery, writer, context)
        }
    }
}
