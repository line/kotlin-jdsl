package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlJoinedEntitySerializer : JpqlSerializer<JpqlJoinedEntity> {
    override fun handledType(): KClass<JpqlJoinedEntity> {
        return JpqlJoinedEntity::class
    }

    override fun serialize(part: JpqlJoinedEntity, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.entity, writer, context)
        writer.write(" ")
        delegate.serialize(part.join, writer, context)
    }
}
