package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlEqualSerializer : JpqlSerializer<JpqlEqual<*>> {
    override fun handledType(): KClass<JpqlEqual<*>> {
        return JpqlEqual::class
    }

    override fun serialize(part: JpqlEqual<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.writeIfAbsent(" ")
        writer.write("=")
        writer.write(" ")

        delegate.serialize(part.compareValue, writer, context)
    }
}
