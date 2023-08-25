package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlInSerializer : JpqlSerializer<JpqlIn<*>> {
    override fun handledType(): KClass<JpqlIn<*>> {
        return JpqlIn::class
    }

    override fun serialize(part: JpqlIn<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        if (IterableUtils.isEmpty(part.compareValues)) {
            return
        }

        delegate.serialize(part.value, writer, context)

        writer.write("IN")
        writer.write(" ")

        writer.writeParentheses {
            writer.writeEach(part.compareValues, separator = ", ") {
                delegate.serialize(it, writer, context)
            }
        }
    }
}
