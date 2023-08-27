package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCase
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCaseSerializer : JpqlSerializer<JpqlCase<*>> {
    override fun handledType(): KClass<JpqlCase<*>> {
        return JpqlCase::class
    }

    override fun serialize(part: JpqlCase<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CASE")
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
