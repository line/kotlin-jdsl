package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAll
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlAllSerializer : JpqlSerializer<JpqlAll<*>> {
    override fun handledType(): KClass<JpqlAll<*>> {
        return JpqlAll::class
    }

    override fun serialize(part: JpqlAll<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("ALL")
        writer.write("(")

        delegate.serialize(part.subquery, writer, context)

        writer.write(")")
    }
}
