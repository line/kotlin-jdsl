package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentTimestamp
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCurrentTimestampSerializer : JpqlSerializer<JpqlCurrentTimestamp> {
    override fun handledType(): KClass<JpqlCurrentTimestamp> {
        return JpqlCurrentTimestamp::class
    }

    override fun serialize(part: JpqlCurrentTimestamp, writer: JpqlWriter, context: RenderContext) {
        writer.write("CURRENT_TIMESTAMP")
    }
}
