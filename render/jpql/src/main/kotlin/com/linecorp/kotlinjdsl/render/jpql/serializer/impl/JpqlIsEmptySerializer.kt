package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsEmpty
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlIsEmptySerializer : JpqlSerializer<JpqlIsEmpty<*, *>> {
    override fun handledType(): KClass<JpqlIsEmpty<*, *>> {
        return JpqlIsEmpty::class
    }

    override fun serialize(part: JpqlIsEmpty<*, *>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.path, writer, context)

        writer.write(" ")
        writer.write("IS EMPTY")
    }
}
