package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalDate
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLocalDateSerializer : JpqlSerializer<JpqlLocalDate> {
    override fun handledType(): KClass<JpqlLocalDate> {
        return JpqlLocalDate::class
    }

    override fun serialize(part: JpqlLocalDate, writer: JpqlWriter, context: RenderContext) {
        writer.write("LOCAL DATE")
    }
}
