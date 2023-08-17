package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotLike
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlNotLikeSerializer : JpqlSerializer<JpqlNotLike> {
    override fun handledType(): KClass<JpqlNotLike> {
        return JpqlNotLike::class
    }

    override fun serialize(part: JpqlNotLike, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.value, writer, context)

        writer.write(" ")
        writer.write("NOT LIKE")
        writer.write(" ")

        delegate.serialize(part.pattern, writer, context)

        part.escape?.let {
            writer.write(" ")
            writer.write("ESCAPE")
            writer.write(" ")
            delegate.serialize(it, writer, context)
        }
    }
}
