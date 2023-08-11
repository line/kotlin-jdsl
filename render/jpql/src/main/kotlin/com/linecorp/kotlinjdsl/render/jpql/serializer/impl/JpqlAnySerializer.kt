package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAny
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlAnySerializer : JpqlSerializer<JpqlAny<*>> {
    override fun handledType(): KClass<JpqlAny<*>> {
        return JpqlAny::class
    }

    override fun serialize(part: JpqlAny<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("ANY")
        writer.write("(")

        delegate.serialize(part.subquery, writer, context)

        writer.write(")")
    }
}
