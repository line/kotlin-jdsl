package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentTime
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCurrentTimeSerializer : JpqlSerializer<JpqlCurrentTime> {
    override fun handledType(): KClass<JpqlCurrentTime> {
        return JpqlCurrentTime::class
    }

    override fun serialize(part: JpqlCurrentTime, writer: JpqlWriter, context: RenderContext) {
        writer.write("CURRENT_TIME")
    }
}
