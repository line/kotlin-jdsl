package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCast
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@SinceJdsl("3.6.0")
class JpqlCastSerializer : JpqlSerializer<JpqlCast<*>> {
    override fun handledType(): KClass<JpqlCast<*>> {
        return JpqlCast::class
    }

    override fun serialize(part: JpqlCast<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CAST")
        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
            writer.write(" AS ")
            writer.write(part.type.simpleName!!)
        }
    }
}
