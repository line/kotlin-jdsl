package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSome
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlSomeSerializer : JpqlSerializer<JpqlSome<*>> {
    override fun handledType(): KClass<JpqlSome<*>> {
        return JpqlSome::class
    }

    override fun serialize(part: JpqlSome<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("SOME")
        writer.write("(")

        delegate.serialize(part.subquery, writer, context)

        writer.write(")")
    }
}
