package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
interface JpqlSerializer<T : QueryPart> {
    @SinceJdsl("3.0.0")
    fun handledType(): KClass<T>

    @SinceJdsl("3.0.0")
    fun serialize(part: T, writer: JpqlWriter, context: RenderContext)
}
