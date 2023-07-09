package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

interface JpqlSerializer<T : QueryPart> {
    fun handledType(): KClass<T>

    fun serialize(part: T, writer: JpqlWriter, context: RenderContext)
}
