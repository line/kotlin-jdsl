package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftFetchJoin
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLeftFetchJoinSerializer : JpqlSerializer<JpqlLeftFetchJoin<*>> {
    override fun handledType(): KClass<JpqlLeftFetchJoin<*>> {
        return JpqlLeftFetchJoin::class
    }

    override fun serialize(part: JpqlLeftFetchJoin<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("LEFT JOIN FETCH")
        writer.write(" ")

        val newJoinContext = context + JpqlRenderClause.Join
        delegate.serialize(part.entity, writer, newJoinContext)

        writer.write(" ")
        writer.write("ON")
        writer.write(" ")

        val newOnContext = context + JpqlRenderClause.On
        delegate.serialize(part.on, writer, newOnContext)
    }
}
