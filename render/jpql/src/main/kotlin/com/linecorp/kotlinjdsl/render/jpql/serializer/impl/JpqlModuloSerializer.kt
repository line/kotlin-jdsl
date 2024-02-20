package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlModulo
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlModuloSerializer : JpqlSerializer<JpqlModulo<*>> {
    override fun handledType(): KClass<JpqlModulo<*>> {
        return JpqlModulo::class
    }

    override fun serialize(part: JpqlModulo<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value1, writer, context)

        writer.write(" ")
        writer.write("%")
        writer.write(" ")

        delegate.serialize(part.value2, writer, context)
    }
}
