package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftJoin
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlLeftJoinSerializer : JpqlSerializer<JpqlLeftJoin<*>> {
    override fun handledType(): KClass<JpqlLeftJoin<*>> {
        return JpqlLeftJoin::class
    }

    override fun serialize(part: JpqlLeftJoin<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write(" ")
        writer.write("LEFT JOIN")
        writer.write(" ")

        delegate.serialize(part.entity, writer, context)

        writer.write(" ")
        writer.write("ON")
        writer.write(" ")

        delegate.serialize(part.on, writer, context)
    }
}
