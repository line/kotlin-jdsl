package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.LocalDateTimeExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLocalDateTimeSerializer : JpqlSerializer<LocalDateTimeExpression> {

    override fun handledType(): KClass<LocalDateTimeExpression> {
        return LocalDateTimeExpression::class
    }

    override fun serialize(part: LocalDateTimeExpression, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("LOCAL DATETIME")

        writer.writeParentheses {
            delegate.serialize(part, writer, context)
        }
    }
}
