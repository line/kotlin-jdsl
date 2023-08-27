package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNew
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlNewSerializer : JpqlSerializer<JpqlNew<*>> {
    override fun handledType(): KClass<JpqlNew<*>> {
        return JpqlNew::class
    }

    override fun serialize(part: JpqlNew<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("NEW")
        writer.write(" ")

        writer.write(part.type.java.name)

        writer.writeParentheses {
            writer.writeEach(part.args, separator = ", ") {
                delegate.serialize(it, writer, context)
            }
        }
    }
}
