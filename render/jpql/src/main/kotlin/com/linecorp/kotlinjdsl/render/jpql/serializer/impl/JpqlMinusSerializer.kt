package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMinus
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlMinusSerializer : JpqlSerializer<JpqlMinus<*>> {
    override fun handledType(): KClass<JpqlMinus<*>> {
        return JpqlMinus::class
    }

    override fun serialize(part: JpqlMinus<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value1, writer, context)

        writer.writeIfAbsent(" ")
        writer.write("-")
        writer.write(" ")

        delegate.serialize(part.value2, writer, context)
    }
}
