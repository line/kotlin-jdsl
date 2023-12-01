package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlConcat
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlConcatSerializer : JpqlSerializer<JpqlConcat> {
    override fun handledType(): KClass<JpqlConcat> {
        return JpqlConcat::class
    }

    override fun serialize(part: JpqlConcat, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CONCAT")

        writer.writeParentheses {
            writer.writeEach(part.values, separator = ", ") {
                delegate.serialize(it, writer, context)
            }
        }
    }
}
