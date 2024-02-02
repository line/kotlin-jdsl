package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrent
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCurrentDateSerializer : JpqlSerializer<JpqlCurrent.CurrentDate> {
    override fun handledType(): KClass<JpqlCurrent.CurrentDate> {
        return JpqlCurrent.CurrentDate::class
    }

    override fun serialize(part: JpqlCurrent.CurrentDate, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("CURRENT_DATE")

        writer.writeParentheses {
            delegate.serialize(part, writer, context)
        }
    }
}
