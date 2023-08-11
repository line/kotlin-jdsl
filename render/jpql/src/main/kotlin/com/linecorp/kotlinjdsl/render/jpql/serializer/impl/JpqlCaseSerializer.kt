package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCase
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlCaseSerializer : JpqlSerializer<JpqlCase<*>> {
    override fun handledType(): KClass<JpqlCase<*>> {
        return JpqlCase::class
    }

    override fun serialize(part: JpqlCase<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CASE")
        writer.writeEach(part.whens.entries, separator = " ") {
            writer.writeIfAbsent(" ")
            writer.write("WHEN ")
            delegate.serialize(it.key, writer, context)
            writer.write(" THEN ")
            delegate.serialize(it.value, writer, context)
        }

        part.`else`?.let {
            writer.write(" ELSE ")
            delegate.serialize(it, writer, context)
        }

        writer.write(" END")
    }
}
