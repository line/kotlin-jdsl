package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFunction
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlFunctionSerializer : JpqlSerializer<JpqlFunction<*>> {
    override fun handledType(): KClass<JpqlFunction<*>> {
        return JpqlFunction::class
    }

    override fun serialize(part: JpqlFunction<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("FUNCTION")
        writer.write("(")

        writer.write(part.name)

        if (IterableUtils.isNotEmpty(part.args)) {
            writer.write(", ")
            writer.write(" ")

            writer.writeEach(part.args, separator = ", ") {
                delegate.serialize(it, writer, context)
            }
        }

        writer.write(")")
    }
}
