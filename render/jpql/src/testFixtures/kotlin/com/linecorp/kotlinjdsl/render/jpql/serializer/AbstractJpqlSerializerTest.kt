package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import org.assertj.core.api.WithAssertions

abstract class AbstractJpqlSerializerTest : WithAssertions {
    fun serialize(
        part: QueryPart,
        serializer: JpqlSerializer<*>,
        writer: JpqlWriter,
        vararg elements: RenderContext.Element
    ) {
        val context = TestRenderContext(elements.toList())

        @Suppress("UNCHECKED_CAST")
        (serializer as JpqlSerializer<QueryPart>).serialize(part, writer, context)
    }
}
