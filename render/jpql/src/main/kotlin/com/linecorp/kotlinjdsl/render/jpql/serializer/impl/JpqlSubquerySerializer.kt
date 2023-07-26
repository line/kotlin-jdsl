package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlSubquerySerializer : JpqlSerializer<JpqlSubquery<*>> {
    override fun handledType(): KClass<JpqlSubquery<*>> {
        return JpqlSubquery::class
    }

    override fun serialize(part: JpqlSubquery<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("(")
        delegate.serialize(part.selectQuery, writer, context)
        writer.write(")")
    }
}
