package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlVersion
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlVersionSerializer : JpqlSerializer<JpqlVersion<*>> {
    override fun handledType(): KClass<JpqlVersion<*>> {
        return JpqlVersion::class
    }

    override fun serialize(part: JpqlVersion<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("VERSION")

        writer.writeParentheses {
            delegate.serialize(part.expr.toExpression(), writer, context)
        }
    }
}
