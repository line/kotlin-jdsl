package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlValueSerializer : JpqlSerializer<JpqlValue<*>> {
    override fun handledType(): KClass<JpqlValue<*>> {
        return JpqlValue::class
    }

    override fun serialize(part: JpqlValue<*>, writer: JpqlWriter, context: RenderContext) {
        val value = part.value

        if (value is KClass<*>) {
            val introspector = context.getValue(JpqlRenderIntrospector)
            val entity = introspector.introspect(value)

            writer.write(entity.name)
        }

        writer.writeParam(part.value)
    }
}
