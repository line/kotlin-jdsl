package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNull
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlIsNullSerializer : JpqlSerializer<JpqlIsNull> {
    override fun handledType(): KClass<JpqlIsNull> {
        return JpqlIsNull::class
    }

    override fun serialize(part: JpqlIsNull, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.expr, writer, context)

        writer.write(" ")
        writer.write("IS NULL")
    }
}
