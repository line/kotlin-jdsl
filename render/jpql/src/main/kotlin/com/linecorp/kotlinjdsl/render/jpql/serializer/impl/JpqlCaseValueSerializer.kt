package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseValue
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlCaseValueSerializer : JpqlSerializer<JpqlCaseValue<*,*>> {
    override fun handledType(): KClass<JpqlCaseValue<*,*>> {
        return JpqlCaseValue::class
    }

    override fun serialize(part: JpqlCaseValue<*,*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CASE")
        writer.write(" ")
        delegate.serialize(part.value, writer, context)

        writer.write(" ")
        writer.writeEach(part.whens.entries, separator = " ") {
            writer.write("WHEN")
            writer.write(" ")
            delegate.serialize(it.key, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            delegate.serialize(it.value, writer, context)
        }

        part.`else`?.let {
            writer.write(" ")
            writer.write("ELSE")
            writer.write(" ")
            delegate.serialize(it, writer, context)
        }

        writer.write(" ")
        writer.write("END")
    }
}
