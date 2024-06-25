package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerJoin
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlInnerJoinSerializer : JpqlSerializer<JpqlInnerJoin<*>> {
    override fun handledType(): KClass<JpqlInnerJoin<*>> {
        return JpqlInnerJoin::class
    }

    override fun serialize(part: JpqlInnerJoin<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("INNER JOIN")
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
