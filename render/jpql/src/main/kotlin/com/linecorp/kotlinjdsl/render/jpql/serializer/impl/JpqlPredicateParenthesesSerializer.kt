package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlPredicateParentheses
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlPredicateParenthesesSerializer : JpqlSerializer<JpqlPredicateParentheses> {
    override fun handledType(): KClass<JpqlPredicateParentheses> {
        return JpqlPredicateParentheses::class
    }

    override fun serialize(part: JpqlPredicateParentheses, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.writeParentheses {
            delegate.serialize(part.predicate, writer, context)
        }
    }
}
