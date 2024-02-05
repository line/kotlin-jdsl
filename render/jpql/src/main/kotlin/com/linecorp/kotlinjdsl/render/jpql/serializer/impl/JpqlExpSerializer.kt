package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExp
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlExpSerializer : JpqlSerializer<JpqlExp<*>> {
    override fun handledType(): KClass<JpqlExp<*>> {
        return JpqlExp::class
    }

    override fun serialize(part: JpqlExp<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("EXP")

        writer.writeParentheses {
            delegate.serialize(part.value, writer, context)
        }
    }
}
