package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerAssociationJoin
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlInnerAssociationJoinSerializer : JpqlSerializer<JpqlInnerAssociationJoin<*>> {
    override fun handledType(): KClass<JpqlInnerAssociationJoin<*>> {
        return JpqlInnerAssociationJoin::class
    }

    override fun serialize(part: JpqlInnerAssociationJoin<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.writeIfAbsent(" ")
        writer.write("INNER JOIN")
        writer.write(" ")

        delegate.serialize(part.association, writer, context)

        writer.writeIfAbsent(" ")

        writer.write("AS")
        writer.write(" ")
        writer.write(part.entity.alias)

        val on = part.on

        if (on != null) {
            writer.writeIfAbsent(" ")
            writer.write("ON")
            writer.write(" ")

            delegate.serialize(on, writer, context)
        }
    }
}
