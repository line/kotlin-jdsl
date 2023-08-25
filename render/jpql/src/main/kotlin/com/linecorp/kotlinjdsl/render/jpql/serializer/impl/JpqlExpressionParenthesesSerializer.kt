package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpressionParentheses
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlExpressionParenthesesSerializer : JpqlSerializer<JpqlExpressionParentheses<*>> {
    override fun handledType(): KClass<JpqlExpressionParentheses<*>> {
        return JpqlExpressionParentheses::class
    }

    override fun serialize(part: JpqlExpressionParentheses<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.writeParentheses {
            delegate.serialize(part.expr, writer, context)
        }
    }
}
