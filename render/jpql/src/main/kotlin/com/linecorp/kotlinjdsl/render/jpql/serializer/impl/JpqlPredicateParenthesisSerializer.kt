package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlPredicateParenthesis
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlPredicateParenthesisSerializer : JpqlSerializer<JpqlPredicateParenthesis> {
    override fun handledType(): KClass<JpqlPredicateParenthesis> {
        return JpqlPredicateParenthesis::class
    }

    override fun serialize(part: JpqlPredicateParenthesis, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("(")

        delegate.serialize(part.predicate, writer, context)

        writer.write(")")
    }
}
