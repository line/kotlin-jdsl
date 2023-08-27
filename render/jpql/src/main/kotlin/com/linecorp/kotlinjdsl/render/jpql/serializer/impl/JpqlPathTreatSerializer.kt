package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathTreat
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlPathTreatSerializer : JpqlSerializer<JpqlPathTreat<*, *>> {
    override fun handledType(): KClass<JpqlPathTreat<*, *>> {
        return JpqlPathTreat::class
    }

    override fun serialize(part: JpqlPathTreat<*, *>, writer: JpqlWriter, context: RenderContext) {
        val introspector = context.getValue(JpqlRenderIntrospector)
        val entity = introspector.introspect(part.type)

        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("TREAT")
        writer.writeParentheses {
            delegate.serialize(part.path, writer, context)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(entity.name)
        }
    }
}
