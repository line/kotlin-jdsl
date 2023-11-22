package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathProperty
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlPathPropertySerializer : JpqlSerializer<JpqlPathProperty<*, *>> {
    override fun handledType(): KClass<JpqlPathProperty<*, *>> {
        return JpqlPathProperty::class
    }

    override fun serialize(part: JpqlPathProperty<*, *>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)
        val introspector = context.getValue(JpqlRenderIntrospector)
        val property = introspector.introspect(part.property)

        delegate.serialize(part.path, writer, context)
        writer.write(".")
        writer.write(property.name)
    }
}
