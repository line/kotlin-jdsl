package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlNullSerializer: JpqlSerializer<JpqlNull> {
    override fun handledType(): KClass<JpqlNull> {
        return JpqlNull::class
    }

    override fun serialize(part: JpqlNull, writer: JpqlWriter, context: RenderContext) {
        writer.write("NULL")
    }
}
