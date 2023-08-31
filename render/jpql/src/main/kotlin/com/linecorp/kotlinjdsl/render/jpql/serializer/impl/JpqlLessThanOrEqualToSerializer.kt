package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanOrEqualTo
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLessThanOrEqualToSerializer : JpqlSerializer<JpqlLessThanOrEqualTo<*>> {
    override fun handledType(): KClass<JpqlLessThanOrEqualTo<*>> {
        return JpqlLessThanOrEqualTo::class
    }

    override fun serialize(part: JpqlLessThanOrEqualTo<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.write(" ")
        writer.write("<=")
        writer.write(" ")

        delegate.serialize(part.compareValue, writer, context)
    }
}
