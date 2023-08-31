package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntityProperty
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlEntityPropertySerializer : JpqlSerializer<JpqlEntityProperty<*, *>> {
    override fun handledType(): KClass<JpqlEntityProperty<*, *>> {
        return JpqlEntityProperty::class
    }

    override fun serialize(part: JpqlEntityProperty<*, *>, writer: JpqlWriter, context: RenderContext) {
        writer.write(part.entity.alias)
        writer.write(".")
        writer.write(part.property.name)
    }
}
