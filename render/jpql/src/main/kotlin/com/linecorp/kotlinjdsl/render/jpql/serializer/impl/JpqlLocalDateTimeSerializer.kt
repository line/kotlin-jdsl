package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalDateTime
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlLocalDateTimeSerializer : JpqlSerializer<JpqlLocalDateTime> {
    override fun handledType(): KClass<JpqlLocalDateTime> {
        return JpqlLocalDateTime::class
    }

    override fun serialize(part: JpqlLocalDateTime, writer: JpqlWriter, context: RenderContext) {
        writer.write("LOCAL DATETIME")
    }
}
