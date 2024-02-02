package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrent
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCurrentTimeSerializer : JpqlSerializer<JpqlCurrent.CurrentTime> {
    override fun handledType(): KClass<JpqlCurrent.CurrentTime> {
        return JpqlCurrent.CurrentTime::class
    }

    override fun serialize(part: JpqlCurrent.CurrentTime, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CURRENT_TIME")

        writer.writeParentheses {
            delegate.serialize(part, writer, context)
        }
    }
}
