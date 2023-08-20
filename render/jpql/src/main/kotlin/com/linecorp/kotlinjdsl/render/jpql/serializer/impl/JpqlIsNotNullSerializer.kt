package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNotNull
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNull
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlIsNotNullSerializer : JpqlSerializer<JpqlIsNotNull> {
    override fun handledType(): KClass<JpqlIsNotNull> {
        return JpqlIsNotNull::class
    }

    override fun serialize(part: JpqlIsNotNull, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.expr, writer, context)

        writer.write(" ")
        writer.write("IS NOT NULL")
    }
}
