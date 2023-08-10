package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlParam
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlParamSerializer : JpqlSerializer<JpqlParam<*>> {
    override fun handledType(): KClass<JpqlParam<*>> {
        return JpqlParam::class
    }

    override fun serialize(part: JpqlParam<*>, writer: JpqlWriter, context: RenderContext) {
        writer.writeParam(part.name, part.value)
    }
}
