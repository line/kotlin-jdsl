package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNotEmpty
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlIsNotEmptySerializer : JpqlSerializer<JpqlIsNotEmpty<*, *>> {
    override fun handledType(): KClass<JpqlIsNotEmpty<*, *>> {
        return JpqlIsNotEmpty::class
    }

    override fun serialize(part: JpqlIsNotEmpty<*, *>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.path, writer, context)

        writer.write(" ")
        writer.write("IS NOT EMPTY")
    }
}
