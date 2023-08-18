package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpressionParenthesis
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlExpressionParenthesisSerializer : JpqlSerializer<JpqlExpressionParenthesis<*>> {
    override fun handledType(): KClass<JpqlExpressionParenthesis<*>> {
        return JpqlExpressionParenthesis::class
    }

    override fun serialize(part: JpqlExpressionParenthesis<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("(")

        delegate.serialize(part.expr, writer, context)

        writer.write(")")
    }
}
