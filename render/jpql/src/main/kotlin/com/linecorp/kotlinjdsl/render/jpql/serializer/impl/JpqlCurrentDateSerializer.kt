package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentDate
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCurrentDateSerializer : JpqlSerializer<JpqlCurrentDate> {
    override fun handledType(): KClass<JpqlCurrentDate> {
        return JpqlCurrentDate::class
    }

    override fun serialize(part: JpqlCurrentDate, writer: JpqlWriter, context: RenderContext) {
        writer.write("CURRENT_DATE")
    }
}
