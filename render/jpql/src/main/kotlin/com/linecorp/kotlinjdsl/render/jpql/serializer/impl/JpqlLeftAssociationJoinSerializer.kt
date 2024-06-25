package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftAssociationJoin
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLeftAssociationJoinSerializer : JpqlSerializer<JpqlLeftAssociationJoin<*>> {
    override fun handledType(): KClass<JpqlLeftAssociationJoin<*>> {
        return JpqlLeftAssociationJoin::class
    }

    override fun serialize(part: JpqlLeftAssociationJoin<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("LEFT JOIN")
        writer.write(" ")

        val newJoinContext = context + JpqlRenderClause.Join
        delegate.serialize(part.association, writer, newJoinContext)

        writer.write(" ")
        writer.write("AS")
        writer.write(" ")

        writer.write(part.entity.alias)

        val on = part.on

        if (on != null) {
            writer.write(" ")
            writer.write("ON")
            writer.write(" ")

            val newOnContext = context + JpqlRenderClause.On
            delegate.serialize(on, writer, newOnContext)
        }
    }
}
