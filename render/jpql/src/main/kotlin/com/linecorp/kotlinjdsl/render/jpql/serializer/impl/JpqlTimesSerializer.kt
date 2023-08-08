package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTimes
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlTimesSerializer : JpqlSerializer<JpqlTimes<*>> {
    override fun handledType(): KClass<JpqlTimes<*>> {
        return JpqlTimes::class
    }

    override fun serialize(part: JpqlTimes<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value1, writer, context)

        writer.write(" ")
        writer.write("*")
        writer.write(" ")

        delegate.serialize(part.value2, writer, context)
    }
}
