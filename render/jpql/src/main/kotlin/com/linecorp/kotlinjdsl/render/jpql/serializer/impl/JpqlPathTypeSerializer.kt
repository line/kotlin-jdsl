package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPathType
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlPathTypeSerializer : JpqlSerializer<JpqlPathType> {
    override fun handledType(): KClass<JpqlPathType> {
        return JpqlPathType::class
    }

    override fun serialize(part: JpqlPathType, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("TYPE")
        writer.writeParentheses {
            delegate.serialize(part.path, writer, context)
        }
    }
}
